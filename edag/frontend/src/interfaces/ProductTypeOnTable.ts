import { ProductType } from "./ProductTypeForSave"

export interface ProductTypeOnTable {
    id: number
    code: string
    name: string
    brand: string
    category: string
    type: string
    description: string
    actions: string
}
