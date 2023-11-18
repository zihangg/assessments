package main

type GraphResponse struct {
	Pool struct {
		Price string `json:"token1Price"`
	} `json:"pool"`
}


type BinanceResponse struct {
	Symbol string `json:"symbol"`
	Price float64 `json:"price"`
}