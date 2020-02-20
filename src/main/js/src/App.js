import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Container } from "react-bootstrap";
import Sidebar from "./components/Sidebar";
import Jumbotron from "./components/Jumbotron";
import Home from "./Home";
import ListUsers from "./ListUsers";
import AddUser from "./AddUser";
import EditUser from "./EditUser";
import NotFound from "./NotFound";

class App extends React.Component {

    render() {
        return (
            <Router>
                <Sidebar/>
                <Jumbotron/>
                <Container>
                    <Switch>
                        <Route exact path="/" component={Home}/>
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
