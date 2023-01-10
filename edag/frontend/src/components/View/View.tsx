import * as React from "react"
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField"
import Dialog from "@mui/material/Dialog"
import DialogActions from "@mui/material/DialogActions"
import DialogContent from "@mui/material/DialogContent"
import DialogTitle from "@mui/material/DialogTitle"
import { ProductTypeOnTable } from "../../interfaces/ProductTypeOnTable"
import { ProductType } from "../../interfaces/ProductTypeForSave"
import AlertDialog from "../Alert/Alert"
import Typography from "@mui/material/Typography"

interface Props {
    newProduct: boolean
    editable: boolean
    setOpen: any
    open: boolean
    data?: ProductTypeOnTable | undefined
}

export default function View(props: Props) {
    const { setOpen, open, editable, data, newProduct } = props
    const [code, setCode] = React.useState("")
    const [name, setName] = React.useState("")
    const [type, setType] = React.useState("")
    const [description, setDescription] = React.useState("")
    const [brand, setBrand] = React.useState("")
    const [category, setCategory] = React.useState("")
    const [alert, setAlert] = React.useState(false)
    const [message, setMessage] = React.useState("")
    const [title, setTitle] = React.useState("")

    React.useEffect(() => {
        if (data !== undefined && newProduct === false) {
            setName(data.name)
            setCode(data.code)
            setType(data.type)
            setDescription(data.description)
            setBrand(data.brand)
            setCategory(data.category)
        }
    }, [data, newProduct])

    const handleClose = () => {
        setOpen(false)
    }

    const handleSave = async () => {
        const body: ProductType = {
            code: code,
            name: name,
            type: type,
            brand: brand,
            category: category,
            description: description,
        }

        if (newProduct) {
            await fetch(`${process.env.REACT_APP_BACKEND_URL}/api/products/`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body),
            })
                .then((res) => res.json())
                .then((data) => {
                    if (data.id) {
                        setAlert(true)
                        setMessage("Successfully saved!")
                        setTitle("Success")
                    }
                })
        } else {
            await fetch(`${process.env.REACT_APP_BACKEND_URL}/api/products/${code}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body),
            })
                .then((res) => res.json())
                .then((data) => {
                    if (data.id) {
                        setAlert(true)
                        setMessage("Successfully updated!")
                        setTitle("Success")
                    }
                })
        }
    }

    return (
        <>
            <div>
                <Dialog open={open} onClose={handleClose} maxWidth="md">
                    {newProduct ? (
                        <DialogTitle>
                            <Typography variant="h5" align="center">
                                Create New Product
                            </Typography>
                        </DialogTitle>
                    ) : (
                        <DialogTitle>
                            <Typography variant="h5" align="center">
                                View Product Information
                            </Typography>
                        </DialogTitle>
                    )}
                    <DialogContent>
                        {newProduct ? (
                            <TextField
                                autoFocus
                                margin="normal"
                                id="code"
                                label="Product Code"
                                fullWidth
                                value={code}
                                variant="standard"
                                onChange={(e) => setCode(e.target.value)}
                                inputProps={{ readOnly: false }}
                            />
                        ) : (
                            <TextField
                                autoFocus
                                margin="normal"
                                id="code"
                                label="Product Code"
                                fullWidth
                                value={code}
                                variant="standard"
                                inputProps={{ readOnly: true }}
                            />
                        )}

                        <TextField
                            autoFocus
                            margin="normal"
                            id="name"
                            label="Product Name"
                            fullWidth
                            value={name}
                            variant="standard"
                            onChange={(e) => setName(e.target.value)}
                            inputProps={{ readOnly: !editable }}
                        />
                        <TextField
                            autoFocus
                            margin="normal"
                            id="brand"
                            label="Product Brand"
                            fullWidth
                            value={brand}
                            variant="standard"
                            onChange={(e) => setBrand(e.target.value)}
                            inputProps={{ readOnly: !editable }}
                        />
                        <TextField
                            autoFocus
                            margin="normal"
                            id="type"
                            label="Product Type"
                            fullWidth
                            value={type}
                            variant="standard"
                            onChange={(e) => setType(e.target.value)}
                            inputProps={{ readOnly: !editable }}
                        />
                        <TextField
                            autoFocus
                            margin="normal"
                            id="category"
                            label="Product Category"
                            fullWidth
                            value={category}
                            variant="standard"
                            onChange={(e) => setCategory(e.target.value)}
                            inputProps={{ readOnly: !editable }}
                        />
                        <TextField
                            autoFocus
                            margin="normal"
                            id="description"
                            label="Product Description"
                            fullWidth
                            value={description}
                            variant="standard"
                            onChange={(e) => setDescription(e.target.value)}
                            inputProps={{ readOnly: !editable }}
                        />
                    </DialogContent>
                    <DialogActions>
                        {editable ? <Button onClick={handleSave}>Save</Button> : <></>}
                        <Button onClick={handleClose}>Close</Button>
                    </DialogActions>
                </Dialog>
                <AlertDialog setOpen={setAlert} open={alert} message={message} title={title} />
            </div>
        </>
    )
}
