import React from "react";
import axios from "axios";
import { Button } from "react-bootstrap";

class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: null,
            loaded: false
        };
    }

    componentDidMount() {
        axios.get("/api/account/" + this.props.match.params.id)
            .then(response => {
                if (response.status === 200) {
                    this.setState({user: response.data});
                }
                this.setState({loaded: true});
            });
    }

    renderForm() {
        if (this.state.loaded) {
            if (this.state.user != null) {
                return (
                    <div>
                        <p>{this.state.user["login"]}</p>
                        <p>{this.state.user["email"]}</p>
                        <p>{this.state.user["firstName"]}</p>
                        <p>{this.state.user["lastName"]}</p>
                    </div>
                );
            } else {
                return <h2>{"User with login: " + this.props.match.params.id + " not found"}</h2>
            }
        }
    }

    render() {
        return (
            <div>
                <h1>Edit user</h1>
                {this.renderForm()}
                <Button onClick={this.props.history.goBack}>Back</Button>
            </div>
        );
    }
}

export default EditUser;
