import React from "react";
import axios from "axios";
import bcrypt from "bcryptjs";
import { Button, Form, FormControl, FormGroup, FormLabel, ToggleButton, ToggleButtonGroup } from "react-bootstrap";

class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            password: "",
            permissions: {"CLIENT": false, "MANAGER": false, "ADMIN": false},
            valid: {"login": true, "password": true, "email": true, "firstName": true, "lastName": true},
            loaded: false
        };
        this.emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    }


    componentDidMount = () => {
        axios.get("/api/account/" + this.props.match.params.id)
            .then(response => {
                let tempPermissions = {...this.state.permissions};
                for (let value of response.data["permissions"]) {
                    tempPermissions[value] = true;
                }
                this.setState({
                    user: response.data,
                    permissions: tempPermissions,
                    loaded: true
                });
            }).catch(error => {
                alert(error.response.data);
                this.props.history.goBack();
            });
    };

    validateProperty = (property) => {
        let tempValid = {...this.state.valid};
        switch (property) {
            case "login":
                tempValid["login"] = document.getElementById("login").value.length !== 0;
                break;
            case "password":
                tempValid["password"] = document.getElementById("password").value.length >= 8;
                break;
            case "email":
                tempValid["email"] = this.emailRegex.test(document.getElementById("email").value);
                break;
            case "firstName":
                tempValid["firstName"] = document.getElementById("firstName").value.length !== 0;
                break;
            case "lastName":
                tempValid["lastName"] = document.getElementById("lastName").value.length !== 0;
                break;
            default:
                break;
        }
        this.setState({valid: tempValid});
    };

    handleChangeProperty = (event, property) => {
        let tempUser = {...this.state.user};
        tempUser[property] = event.target.value;
        this.setState({user: tempUser});
        this.validateProperty(property);
    };

    handleChangeActivity = () => {
        let tempUser = {...this.state.user};
        tempUser["active"] = !this.state.user["active"];
        this.setState({user: tempUser});
    };

    handleChangePassword = (event) => {
        this.setState({password: event.target.value});
        this.validateProperty("password");
    };

    handleChangePermissions = (access) => {
        let tempPermissions = {...this.state.permissions};
        tempPermissions[access] = !this.state.permissions[access];
        this.setState({permissions: tempPermissions});
    };

    checkValidation = () => {
        let validated = true;
        let tempValid = {...this.state.valid};
        tempValid["login"] = document.getElementById("login").value.length !== 0;
        tempValid["password"] = document.getElementById("password").value.length >= 8;
        tempValid["email"] = this.emailRegex.test(document.getElementById("email").value);
        tempValid["firstName"] = document.getElementById("firstName").value.length !== 0;
        tempValid["lastName"] = document.getElementById("lastName").value.length !== 0;
        for (let key in tempValid) {
            if (tempValid.hasOwnProperty(key) && tempValid[key] === false) {
                this.validateProperty(key);
                validated = false;
                break;
            }
        }
        return validated;
    };

    handleSubmit = () => {
        let message = "";
        let tempPermissions = [];
        for (let key in this.state.permissions) {
            if (this.state.permissions.hasOwnProperty(key) && this.state.permissions[key] === true) {
                tempPermissions.push(key);
            }
        }
        let formValidated = this.checkValidation();
        let permissionsValidated = tempPermissions.length !== 0;
        if (!formValidated && permissionsValidated) {
            message += "Please fill out every field in the form."
        } else if (formValidated && !permissionsValidated) {
            message += "Please choose at least one permission."
        } else if (!formValidated && !permissionsValidated) {
            message += "Please fill out every field in the form.\nPlease choose at least one permission."
        }
        if (formValidated && permissionsValidated) {
            let tempUser = {...this.state.user};
            tempUser["password"] = bcrypt.hashSync(this.state.password, 12);
            tempUser["permissions"] = tempPermissions;
            axios.put("/api/account/" + this.props.match.params.id, this.state.user)
                .then(response => {
                    alert(response.data);
                    this.props.history.push("/listusers");
                }).catch(error => {
                alert(error.response.data);
            });
        } else {
            alert(message);
        }
    };

    renderForm = () => {
        if (this.state.loaded) {
            if (this.state.user !== {}) {
                const checkActive = () => {
                    if (this.state.user["active"]) {
                        return "active";
                    } else {
                        return "inactive";
                    }
                };
                return (
                    <Form>
                        <FormGroup>
                            <FormLabel>Username</FormLabel>
                            <FormControl id="login" value={this.state.user["login"]} onChange={(event) => this.handleChangeProperty(event, "login")} isInvalid={!this.state.valid["login"]}/>
                            <FormControl.Feedback id="control" type="invalid">Please provide a username.</FormControl.Feedback>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Password</FormLabel>
                            <FormControl id="password" value={this.state.password} onChange={this.handleChangePassword} type="password" isInvalid={!this.state.valid["password"]}/>
                            <FormControl.Feedback type="invalid">Password must be at least 8 characters long.</FormControl.Feedback>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Email address</FormLabel>
                            <FormControl id="email" value={this.state.user["email"]} onChange={(event) => this.handleChangeProperty(event, "email")} isInvalid={!this.state.valid["email"]}/>
                            <FormControl.Feedback type="invalid">Please provide a valid email address.</FormControl.Feedback>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>First name</FormLabel>
                            <FormControl id="firstName" value={this.state.user["firstName"]} onChange={(event) => this.handleChangeProperty(event, "firstName")} isInvalid={!this.state.valid["firstName"]}/>
                            <FormControl.Feedback type="invalid">Please provide a first name.</FormControl.Feedback>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Last name</FormLabel>
                            <FormControl id="lastName" value={this.state.user["lastName"]} onChange={(event) => this.handleChangeProperty(event, "lastName")} isInvalid={!this.state.valid["lastName"]}/>
                            <FormControl.Feedback type="invalid">Please provide a last name.</FormControl.Feedback>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Permissions</FormLabel>
                            <div>
                                <ToggleButtonGroup defaultValue={this.state.user["permissions"]} type="checkbox">
                                    <ToggleButton value="CLIENT" variant="outline-primary" checked={this.state.permissions["CLIENT"]} onChange={() => this.handleChangePermissions("CLIENT")}>Client</ToggleButton>
                                    <ToggleButton value="MANAGER" variant="outline-primary" checked={this.state.permissions["MANAGER"]} onChange={() => this.handleChangePermissions("MANAGER")}>Manager</ToggleButton>
                                    <ToggleButton value="ADMIN" variant="outline-primary" checked={this.state.permissions["ADMIN"]} onChange={() => this.handleChangePermissions("ADMIN")}>Admin</ToggleButton>
                                </ToggleButtonGroup>
                            </div>
                        </FormGroup>

                        <FormGroup>
                            <FormLabel>Activity</FormLabel>
                            <div>
                                <ToggleButtonGroup name="activity" defaultValue={checkActive()} type="radio">
                                    <ToggleButton value="active" variant="outline-primary" checked={this.state.user["active"]} onChange={this.handleChangeActivity}>Active</ToggleButton>
                                    <ToggleButton value="inactive" variant="outline-primary" checked={!this.state.user["active"]} onChange={this.handleChangeActivity}>Inactive</ToggleButton>
                                </ToggleButtonGroup>
                            </div>
                        </FormGroup>
                        <hr/>
                        <Button onClick={this.handleSubmit}>Submit</Button>
                    </Form>
                );
            } else {
                return <h2>{"User with login: " + this.props.match.params.id + " not found"}</h2>
            }
        }
    };

    render() {
        return (
            <div>
                <h1>Edit user</h1>
                <hr/>
                {this.renderForm()}
                <Button style={{marginTop: "1em"}} onClick={this.props.history.goBack}>Back</Button>
            </div>
        );
    }
}

export default EditUser;
