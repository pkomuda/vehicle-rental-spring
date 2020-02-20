import React from "react";
import axios from "axios";
import { Button, Form, FormControl, FormGroup, FormLabel } from "react-bootstrap";

class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            loaded: false
        };
    }

    componentDidMount() {
        axios.get("/api/account/" + this.props.match.params.id)
            .then(response => {
                this.setState({
                    user: response.data,
                    loaded: true
                });
            }).catch(error => {
                alert(error.response.data);
                this.props.history.goBack();
            });
    }

    handleChangeUsername = (event) => {
        let tempUser = {...this.state.user};
        tempUser["login"] = event.target.value;
        this.setState({user: tempUser});
    };

    handleChangeEmail = (event) => {
        let tempUser = {...this.state.user};
        tempUser["email"] = event.target.value;
        this.setState({user: tempUser});
    };

    handleChangeFirstName = (event) => {
        let tempUser = {...this.state.user};
        tempUser["firstName"] = event.target.value;
        this.setState({user: tempUser});
    };

    handleChangeLastName = (event) => {
        let tempUser = {...this.state.user};
        tempUser["lastName"] = event.target.value;
        this.setState({user: tempUser});
    };

    handleSubmit = () => {
        axios.put("/api/account/" + this.props.match.params.id, this.state.user)
            .then(response => {
                alert(response.data);
                this.props.history.push("/listusers");
            }).catch(error => {
                alert(error.response.data);
            });
    };

    renderForm() {
        if (this.state.loaded) {
            if (this.state.user !== {}) {
                return (
                    <Form>
                        <FormGroup>
                            <FormLabel>Username</FormLabel>
                            <FormControl value={this.state.user["login"]} onChange={this.handleChangeUsername}/>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Email address</FormLabel>
                            <FormControl value={this.state.user["email"]} onChange={this.handleChangeEmail}/>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>First name</FormLabel>
                            <FormControl value={this.state.user["firstName"]} onChange={this.handleChangeFirstName}/>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>First name</FormLabel>
                            <FormControl value={this.state.user["lastName"]} onChange={this.handleChangeLastName}/>
                        </FormGroup>

                        <Button onClick={this.handleSubmit}>Submit</Button>
                    </Form>
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
                <Button style={{marginTop: "1em"}} onClick={this.props.history.goBack}>Back</Button>
            </div>
        );
    }
}

export default EditUser;
