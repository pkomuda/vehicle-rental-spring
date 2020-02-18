import React from "react";
import axios from "axios";
import { Button, FormControl } from "react-bootstrap";
import Table from "./components/Table"

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            users: [],
            columns: [],
            loaded: false
        };
    }

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
        let table = [];
        if (this.state.loaded) {
            table.push(
                <Table keyField="login" data={this.state.users} columns={this.state.columns}/>
            );
        }
        return table;
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

export default Users;
