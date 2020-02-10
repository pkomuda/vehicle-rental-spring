import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Home";
import Users from "./Users";
import NotFound from "./NotFound";

class App extends React.Component {

    render() {
        return (
            <React.Fragment>
                <Router>
                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route path="/users" component={Users}/>
                        <Route component={NotFound}/>
                    </Switch>
                </Router>
            </React.Fragment>
        )
    }
}

export default App;
