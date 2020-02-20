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

    handleHome = (event) => {
        event.preventDefault();
        if (this.props.location.pathname !== "/") {
            this.props.history.push("/");
        }
        this.closeMenu();
    };

    handleUsers = (event) => {
        event.preventDefault();
        if (this.props.location.pathname !== "/listusers") {
            this.props.history.push("/listusers");
        }
        this.closeMenu();
    };

    render () {
        return (
            <Menu isOpen={this.state.menuOpen} onStateChange={(state) => this.handleStateChange(state)}>
                <a onClick={this.handleHome} className="menu-item" href="/">Home</a>
                <a onClick={this.handleUsers} className="menu-item" href="/">Users</a>
            </Menu>
        );
    }
}

export default withRouter(Sidebar);
