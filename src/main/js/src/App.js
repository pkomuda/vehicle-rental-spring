import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Container } from "react-bootstrap";
import Sidebar from "./components/utils/Sidebar";
import Jumbotron from "./components/utils/Jumbotron";
import Home from "./components/main/Home";
import Login from "./components/main/Login";
import Register from "./components/main/Register";
import ListUsers from "./components/users/ListUsers";
import AddUser from "./components/users/AddUser";
import EditUser from "./components/users/EditUser";
import NotFound from "./components/main/NotFound";

class App extends React.Component {

    render() {
        return (
            <Router>
                <Sidebar/>
                <Jumbotron/>
                <Container>
                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route path="/login" component={Login}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/listusers" component={ListUsers}/>
                        <Route path="/adduser" component={AddUser}/>
                        <Route path="/edituser/:id" component={EditUser}/>
                        <Route component={NotFound}/>
                    </Switch>
                </Container>
            </Router>
        )
    }
}

export default App;
