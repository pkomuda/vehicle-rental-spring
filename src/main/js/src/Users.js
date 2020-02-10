import React from "react";
import axios from "axios";
import { Button, Table } from "react-bootstrap"

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.state = {users: []};
    }

    componentDidMount() {
        axios.get("/api/accounts")
            .then(response => {
                this.setState({users: response.data})
            });
    }

    renderTable() {
        let rows = [];
        for (let i = 0; i < this.state.users.length; i++) {
            rows.push(
                <tr key={i}>
                    <td>{i}</td>
                    <td>{this.state.users[i]["login"]}</td>
                </tr>
            );
        }
        return rows;
    }

    render() {
        return (
            <div>
                <h1>Users</h1>
                <Table bordered hover>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Username</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderTable()}
                    </tbody>
                </Table>
                <Button onClick={this.props.history.goBack}>Back</Button>
            </div>
        )
    }
}

export default Users;
