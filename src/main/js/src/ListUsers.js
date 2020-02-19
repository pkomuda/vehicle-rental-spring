import React from "react";
import axios from "axios";
import { Button, FormControl } from "react-bootstrap";
import Table from "./components/Table"

class ListUsers extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            users: [],
            columns: [],
            loaded: false
        };
    }

    editButtonFormatter = (cell, row) => {
        const handleEdit = (login) => {
            this.props.history.push("/edituser/" + login);
        };
        return <Button onClick={() => handleEdit(row["login"])}>Edit</Button>;
    };

    deleteButtonFormatter = (cell, row) => {
        const handleDelete = (login) => {
            if (window.confirm("Do you really want to delete user: " + login + "?")) {
                axios.delete("/api/account/" + login)
                    .then(response => {
                        if (response.status !== 200) {
                            alert(response.body);
                        } else {
                            axios.get("/api/accounts")
                                .then(response => {
                                    this.setState({users: response.data});
                                });
                        }
                    });
            }
        };
        return <Button onClick={() => handleDelete(row["login"])}>Delete</Button>;
    };

    componentDidMount() {
        axios.get("/api/accounts")
            .then(response => {
                this.setState({
                    users: response.data,
                    columns: [{
                        dataField: "login",
                        text: 'Username',
                        sort: true
                    }, {
                        dataField: "email",
                        text: "Email address",
                        sort: true
                    }, {
                        dataField: "firstName",
                        text: "First name",
                        sort: true
                    }, {
                        dataField: "lastName",
                        text: "Last name",
                        sort: true
                    }, {
                        dataField: "edit",
                        text: "Edit",
                        isDummyField: true,
                        formatter: this.editButtonFormatter
                    }, {
                        dataField: "delete",
                        text: "Delete",
                        isDummyField: true,
                        formatter: this.deleteButtonFormatter
                    }],
                    loaded: true
                });
            });
    }

    handleSearch(value) {
        axios.get("/api/accounts/" + value)
            .then(response => {
                this.setState({users: response.data});
            });
    }

    renderTable() {
        if (this.state.loaded) {
            return <Table keyField="login" data={this.state.users} columns={this.state.columns}/>;
        }
    }

    render() {
        return (
            <div>
                <h1>Users</h1>
                <FormControl placeholder="Search" id="searchBar" onChange={() => this.handleSearch(document.getElementById("searchBar").value)}/>
                <hr/>
                {this.renderTable()}
                <Button onClick={this.props.history.goBack}>Back</Button>
            </div>
        )
    }
}

export default ListUsers;
