import React from "react"
import "./App.css"
import Product from "./components/Product/Product"
import "@fontsource/roboto/300.css"
import "@fontsource/roboto/400.css"
import "@fontsource/roboto/500.css"
import "@fontsource/roboto/700.css"

function App() {
    return (
        <>
            <div className="product-container">
                <Product />
            </div>
        </>
    )
}

export default App
