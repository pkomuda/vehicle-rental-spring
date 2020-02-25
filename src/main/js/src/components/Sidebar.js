import React from "react";
import { withRouter } from "react-router-dom";
import { stack as Menu } from "react-burger-menu";
import "./Sidebar.css"

class Sidebar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {menuOpen: false};
    }

    closeMenu = () => {
        this.setState({menuOpen: false})
    };

    handleStateChange = (state) => {
        this.setState({menuOpen: state.isOpen});
    };

    handleRedirect = (event, page) => {
        event.preventDefault();
        if (this.props.location.pathname !== "/" + page) {
            this.props.history.push("/" + page);
        }
        this.closeMenu();
    };

    render () {
        return (
            <Menu isOpen={this.state.menuOpen} onStateChange={this.handleStateChange}>
                <a onClick={(event) => this.handleRedirect(event, "")} className="menu-item" href="/">Home</a>
                <a onClick={(event) => this.handleRedirect(event, "login")} className="menu-item" href="/">Login</a>
                <a onClick={(event) => this.handleRedirect(event, "register")} className="menu-item" href="/">Register</a>
                <a onClick={(event) => this.handleRedirect(event, "listusers")} className="menu-item" href="/">Users</a>
            </Menu>
        );
    }
}

export default withRouter(Sidebar);
