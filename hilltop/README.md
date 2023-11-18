# Introduction

This is a simple arbitrage checker between Binance and Uniswap for Hilltop's assessment.

## Getting Started

1. Run `cp .env-sample .env`
2. Fill in your Binance API key & secret.
3. Run `go run *.go` to run the application.
4. Ctrl+C to stop the application.

### Caveats

1. This is not true "real-time", as it calls the APIs once per second. This can be further improved upon by using websockets. Unfortunately since Uniswap's graph do not support `subscription`, this was not done.
2. There is no consideration for Uniswap fees & gas fees on the blockchain, which would probably cause the results to be not 100% accurate.
3. There is no % check as to whether or not a certain arbitrage % difference is there prior to returning `true` or `false`.
