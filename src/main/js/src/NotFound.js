import React from "react";
import { Button } from "react-bootstrap";

class NotFound extends React.Component {

    render() {
        return (
            <div>
                <h1>Page not found</h1>
                <Button style={{marginTop: "1em"}} onClick={this.props.history.goBack}>Back</Button>
            </div>
        )
    }
}

export default NotFound;
