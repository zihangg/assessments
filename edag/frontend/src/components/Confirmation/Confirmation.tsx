import * as React from "react"
import Button from "@mui/material/Button"
import Dialog from "@mui/material/Dialog"
import DialogActions from "@mui/material/DialogActions"
import DialogContent from "@mui/material/DialogContent"
import DialogContentText from "@mui/material/DialogContentText"
import DialogTitle from "@mui/material/DialogTitle"
import AlertDialog from "../Alert/Alert"

interface Props {
    setOpen: any
    open: any
    code: string
}

export default function ConfirmationDialog(props: Props) {
    const { setOpen, open, code } = props
    const [message, setMessage] = React.useState("")
    const [title, setTitle] = React.useState("")
    const [alert, setAlert] = React.useState(false)

    const handleClose = () => {
        setOpen(false)
    }

    const handleDelete = async () => {
        try {
            await fetch(`${process.env.REACT_APP_BACKEND_URL}/api/products/${code}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" },
            })

            setAlert(true)
            setOpen(false)
            setMessage(`Successfully deleted product with code ${code}`)
            setTitle("Success")
        } catch (error) {
            setAlert(true)
            setOpen(false)
            setMessage("Something went wrong, please try again.")
            setTitle("Error")
        }
    }

    return (
        <div>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">Confirmation</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to delete {code}?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDelete} autoFocus>
                        OK
                    </Button>
                    <Button onClick={handleClose} autoFocus>
                        Cancel
                    </Button>
                </DialogActions>
            </Dialog>
            <AlertDialog setOpen={setAlert} open={alert} message={message} title={title} />
        </div>
    )
}
