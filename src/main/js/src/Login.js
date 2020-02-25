import React from "react";
import axios from "axios";
import bcrypt from "bcryptjs";
import { Form, FormControl, FormGroup, FormLabel } from "react-bootstrap";
import CenterButton from "./components/CenterButton";

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            login: "",
            password: "",
            valid: {"login": true, "password": true}
        };
    }

    validateProperty = (property) => {
        let tempValid = {...this.state.valid};
        switch (property) {
            case "login":
                tempValid["login"] = document.getElementById("login").value.length !== 0;
                break;
            case "password":
                tempValid["password"] = document.getElementById("password").value.length >= 8;
                break;
            default:
                break;
        }
        this.setState({valid: tempValid});
    };

    handleChangeLogin = (event) => {
        this.setState({login: event.target.value});
        this.validateProperty("login");
    };

    handleChangePassword = (event) => {
        this.setState({password: event.target.value});
        this.validateProperty("password");
    };

    checkValidation = () => {
        let validated = true;
        let tempValid = {...this.state.valid};
        tempValid["login"] = document.getElementById("login").value.length !== 0;
        tempValid["password"] = document.getElementById("password").value.length >= 8;
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
        if (this.checkValidation()) {
            let user = {"login": this.state.login, "password": bcrypt.hashSync(this.state.password, 12)};
            console.log(user);
            // //TODO
            // axios.post("/api/login", user)
            //     .then(response => {
            //         this.props.history.push("/");
            //     }).catch(error => {
            //         alert(error.response.data);
            // });
        } else {
            alert("Please fill out every field in the form.");
        }
    };

    render() {
        return (
            <div>
                <h1>Login</h1>
                <hr/>
                <Form>
                    <FormGroup>
                        <FormLabel>Username</FormLabel>
                        <FormControl id="login" value={this.state.login} onChange={this.handleChangeLogin} isInvalid={!this.state.valid["login"]}/>
                        <FormControl.Feedback id="control" type="invalid">Please provide a username.</FormControl.Feedback>
                    </FormGroup>

                    <FormGroup>
                        <FormLabel>Password</FormLabel>
                        <FormControl id="password" value={this.state.password} onChange={this.handleChangePassword} type="password" isInvalid={!this.state.valid["password"]}/>
                        <FormControl.Feedback type="invalid">Password must be at least 8 characters long.</FormControl.Feedback>
                    </FormGroup>
                    <hr/>
                    <CenterButton onClick={this.handleSubmit} text="Submit"/>
                </Form>
                <CenterButton back onClick={this.props.history.goBack} text="Back"/>
            </div>
        );
    }
}

export default Login;
