import React from "react";
import { withRouter } from "react-router-dom";
import { stack as Menu } from "react-burger-menu";
import "./Sidebar.css"

class Sidebar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {menuOpen: false};
    }

    handleStateChange(state) {
        this.setState({menuOpen: state.isOpen});
    }

    closeMenu() {
        this.setState({menuOpen: false})
    }

    handleUsers = (event) => {
        event.preventDefault();
        if (this.props.location.pathname !== "/users") {
            this.props.history.push("/users");
        }
        this.closeMenu();
    };

    render () {
        return (
            <Menu isOpen={this.state.menuOpen} onStateChange={(state) => this.handleStateChange(state)}>
                <a onClick={this.handleUsers} className="menu-item" href="">Users</a>
            </Menu>
        );
    }
}

export default withRouter(Sidebar);
