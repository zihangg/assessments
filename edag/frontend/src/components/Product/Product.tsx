import * as React from "react"
import Box from "@mui/material/Box"
import Table from "@mui/material/Table"
import TableBody from "@mui/material/TableBody"
import TableCell from "@mui/material/TableCell"
import TableContainer from "@mui/material/TableContainer"
import TableHead from "@mui/material/TableHead"
import TablePagination from "@mui/material/TablePagination"
import TableRow from "@mui/material/TableRow"
import TableSortLabel from "@mui/material/TableSortLabel"
import Paper from "@mui/material/Paper"
import IconButton from "@mui/material/IconButton"
import Tooltip from "@mui/material/Tooltip"
import DeleteIcon from "@mui/icons-material/Delete"
import { visuallyHidden } from "@mui/utils"
import { Add, Edit, Visibility } from "@mui/icons-material"
import View from "../View/View"
import { ProductTypeOnTable } from "../../interfaces/ProductTypeOnTable"
import Toolbar from "@mui/material/Toolbar"
import Typography from "@mui/material/Typography"
import ConfirmationDialog from "../Confirmation/Confirmation"

type Order = "asc" | "desc"

function descendingComparator<T>(a: T, b: T, orderBy: keyof T) {
    if (b[orderBy] < a[orderBy]) {
        return -1
    }
    if (b[orderBy] > a[orderBy]) {
        return 1
    }
    return 0
}

function getComparator<Key extends keyof any>(
    order: Order,
    orderBy: Key
): (a: { [key in Key]: number | string }, b: { [key in Key]: number | string }) => number {
    return order === "desc"
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy)
}

function stableSort<T>(array: readonly T[], comparator: (a: T, b: T) => number) {
    const stabilizedThis = array.map((el, index) => [el, index] as [T, number])
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0])
        if (order !== 0) {
            return order
        }
        return a[1] - b[1]
    })
    return stabilizedThis.map((el) => el[0])
}

interface HeadCell {
    disablePadding: boolean
    id: keyof ProductTypeOnTable
    label: string
    numeric: boolean
}

const headCells: readonly HeadCell[] = [
    {
        id: "id",
        numeric: true,
        disablePadding: false,
        label: "ID",
    },
    {
        id: "code",
        numeric: false,
        disablePadding: true,
        label: "Product Code",
    },
    {
        id: "name",
        numeric: false,
        disablePadding: false,
        label: "Name",
    },
    {
        id: "brand",
        numeric: false,
        disablePadding: false,
        label: "Brand",
    },
    {
        id: "category",
        numeric: false,
        disablePadding: false,
        label: "Category",
    },
    {
        id: "type",
        numeric: false,
        disablePadding: false,
        label: "Type",
    },
    {
        id: "actions",
        numeric: false,
        disablePadding: false,
        label: "Actions",
    },
]

const EnhancedTableToolbar = () => {
    const [openView, setOpenView] = React.useState(false)

    const handleAdd = () => {
        setOpenView(true)
    }
    return (
        <>
            <Toolbar
                sx={{
                    pl: { sm: 2 },
                    pr: { xs: 1, sm: 1 },
                }}
            >
                <Typography sx={{ flex: "1 1 100%" }} variant="h6" id="tableTitle" component="div">
                    Products
                </Typography>
                <Tooltip title="Add Product">
                    <IconButton onClick={() => handleAdd()}>
                        <Add />
                    </IconButton>
                </Tooltip>
            </Toolbar>
            <View newProduct={true} editable={true} setOpen={setOpenView} open={openView} />
        </>
    )
}

interface EnhancedTableProps {
    onRequestSort: (event: React.MouseEvent<unknown>, property: keyof ProductTypeOnTable) => void
    order: Order
    orderBy: string
    rowCount: number
}

function EnhancedTableHead(props: EnhancedTableProps) {
    const { order, orderBy, onRequestSort } = props
    const createSortHandler =
        (property: keyof ProductTypeOnTable) => (event: React.MouseEvent<unknown>) => {
            onRequestSort(event, property)
        }

    return (
        <TableHead>
            <TableRow>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align="left"
                        padding={headCell.disablePadding ? "none" : "normal"}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : "asc"}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === "desc" ? "sorted descending" : "sorted ascending"}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    )
}

export default function EnhancedTable() {
    const [order, setOrder] = React.useState<Order>("asc")
    const [orderBy, setOrderBy] = React.useState<keyof ProductTypeOnTable>("id")
    const [page, setPage] = React.useState(0)
    const [rowsPerPage, setRowsPerPage] = React.useState(10)
    const [data, setData] = React.useState<ProductTypeOnTable[]>([])
    const [openView, setOpenView] = React.useState(false)
    const [viewData, setViewData] = React.useState<ProductTypeOnTable>()
    const [editable, setEditable] = React.useState(false)
    const [confirmation, setConfirmation] = React.useState(false)
    const [code, setCode] = React.useState("")

    // load all data
    React.useEffect(() => {
        fetch(`${process.env.REACT_APP_BACKEND_URL}/api/products?page=1&size=100`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        })
            .then((res) => res.json())
            .then((data) => {
                setData(data)
            })
    }, [page])

    const handleRequestSort = (
        event: React.MouseEvent<unknown>,
        property: keyof ProductTypeOnTable
    ) => {
        const isAsc = orderBy === property && order === "asc"
        setOrder(isAsc ? "desc" : "asc")
        setOrderBy(property)
    }

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage)
    }

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10))
        setPage(0)
    }

    const handleOpenModal = async (editMode: boolean, code: string) => {
        const product: ProductTypeOnTable = await fetch(
            `${process.env.REACT_APP_BACKEND_URL}/api/products/${code}`,
            {
                method: "GET",
                headers: { "Content-Type": "application/json" },
            }
        ).then((res) => res.json())

        setEditable(editMode)
        setOpenView(true)
        setViewData(product)
    }

    const handleDelete = async (code: string) => {
        setConfirmation(true)
        setCode(code)
    }

    return (
        <>
            <Box sx={{ width: "100%" }}>
                <Paper sx={{ width: "100%", mb: 2 }}>
                    <TableContainer>
                        <EnhancedTableToolbar />
                        <Table sx={{ minWidth: 750 }} aria-labelledby="tableTitle">
                            <EnhancedTableHead
                                order={order}
                                orderBy={orderBy}
                                onRequestSort={handleRequestSort}
                                rowCount={data.length}
                            />
                            <TableBody>
                                {stableSort(data, getComparator(order, orderBy))
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((row, index) => {
                                        const labelId = `enhanced-table-checkbox-${index}`

                                        return (
                                            <TableRow
                                                hover
                                                role="checkbox"
                                                tabIndex={-1}
                                                key={row.id}
                                            >
                                                <TableCell align="left">{row.id}</TableCell>
                                                <TableCell
                                                    component="th"
                                                    id={labelId}
                                                    scope="row"
                                                    padding="none"
                                                    align="left"
                                                >
                                                    {row.code}
                                                </TableCell>
                                                <TableCell align="left">{row.name}</TableCell>
                                                <TableCell align="left">{row.brand}</TableCell>
                                                <TableCell align="left">{row.type}</TableCell>
                                                <TableCell align="left">{row.category}</TableCell>
                                                <TableCell align="left">
                                                    <Tooltip title="View">
                                                        <IconButton
                                                            onClick={() => {
                                                                handleOpenModal(false, row.code)
                                                            }}
                                                        >
                                                            <Visibility />
                                                        </IconButton>
                                                    </Tooltip>
                                                    <Tooltip title="Edit">
                                                        <IconButton
                                                            onClick={() => {
                                                                handleOpenModal(true, row.code)
                                                            }}
                                                        >
                                                            <Edit />
                                                        </IconButton>
                                                    </Tooltip>
                                                    <Tooltip title="Delete">
                                                        <IconButton
                                                            onClick={() => {
                                                                handleDelete(row.code)
                                                            }}
                                                        >
                                                            <DeleteIcon />
                                                        </IconButton>
                                                    </Tooltip>
                                                </TableCell>
                                            </TableRow>
                                        )
                                    })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[10, 15, 20, 25]}
                        component="div"
                        count={data.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </Paper>
            </Box>
            <View
                newProduct={false}
                editable={editable}
                setOpen={setOpenView}
                open={openView}
                data={viewData}
            />
            <ConfirmationDialog setOpen={setConfirmation} open={confirmation} code={code} />
        </>
    )
}
