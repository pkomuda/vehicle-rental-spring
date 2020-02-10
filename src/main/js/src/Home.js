import React from "react";
import { Button } from "react-bootstrap"

class Home extends React.Component {

    handleUsers() {
        this.props.history.push("/users");
    }

    render() {
        return (
            <div>
                <h1>Vehicle Rental</h1>
                <Button onClick={() => this.handleUsers()}>Users</Button>
            </div>
        )
    }
}

export default Home;
