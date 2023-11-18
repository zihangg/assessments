package main

import (
	"context"
	"fmt"
	"log"
	"os"
	"strconv"
	"time"

	"github.com/adshao/go-binance/v2"
	"github.com/joho/godotenv"
	"github.com/machinebox/graphql"
)

func init() {
	err := godotenv.Load(".env");

	if err != nil {
		log.Fatal("Error loading .env file");
	}

}

func main() {

	ctx := context.Background()

	uClient := graphql.NewClient("https://api.thegraph.com/subgraphs/name/uniswap/uniswap-v3")
	bClient := binance.NewClient(os.Getenv("BINANCE_API_KEY"), os.Getenv("BINANCE_API_SECRET"))

	ticker := time.NewTicker(1 * time.Second)
	defer ticker.Stop()

	for range ticker.C {
		uPrice := getPriceFromUniswap(uClient, ctx)
		bPrice := getPriceFromBinance(bClient, ctx)
		
		var arbitrage string
		if (uPrice > bPrice) {
			arbitrage = "Uniswap"
		} else {
			arbitrage = "Binance"
		}

	
		log.Printf("Uniswap price: %s", uPrice)
		log.Printf("Binance price: %s", bPrice)
		log.Printf("Arbitrage from: %s", arbitrage)
		log.Println("--------------------------")
	}
}

func getPriceFromBinance(client *binance.Client, ctx context.Context) string {
	price, err := client.NewListPricesService().Symbol(os.Getenv("TICKER")).Do(ctx)

	if err != nil {
		log.Fatal("Error getting prices from Binance:", err)
	}

	return decimalHandler(price[len(price)-1].Price)
}

func getPriceFromUniswap(client *graphql.Client, ctx context.Context) string {
	queryTemplate := `
		query ethPrice {
			pool(id: "%s") {
				token1Price
			}
		}
	`

	poolId := os.Getenv("UNISWAP_POOL")

	queryString := fmt.Sprintf(queryTemplate, poolId)
	req := graphql.NewRequest(queryString)

	var gResp GraphResponse
	if err := client.Run(ctx, req, &gResp); err != nil {
			log.Fatal(err)
	}

	return decimalHandler(gResp.Pool.Price)
}

func decimalHandler(price string) string {
	floatPrice, _ := strconv.ParseFloat(price, 64)

	return fmt.Sprintf("%.8f", floatPrice)
}