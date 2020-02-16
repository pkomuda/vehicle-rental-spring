import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Home";
import Users from "./Users";
import NotFound from "./NotFound";
import Sidebar from "./components/Sidebar";
import Jumbotron from "./components/Jumbotron";
import Layout from "./components/Layout";

class App extends React.Component {

    render() {
        return (
            <React.Fragment>
                <Router>
                    <Sidebar/>
                    <Jumbotron/>
                    <Layout>
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route path="/users" component={Users}/>
                            <Route component={NotFound}/>
                        </Switch>
                    </Layout>
                </Router>
            </React.Fragment>
        )
    }
}

export default App;
