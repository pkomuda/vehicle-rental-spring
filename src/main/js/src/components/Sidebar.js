import React from "react";
import { withRouter } from "react-router-dom";
import { stack as Menu } from "react-burger-menu";
import "./Sidebar.css"

class Sidebar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            hover: false,
            menuOpen: false
        };
    }

    handleHome = () => {
        if (this.props.location.pathname !== "/") {
            this.props.history.push("/");
        }
    };

    toggleHover = () => {
        this.setState({hover: !this.state.hover});
    };

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
        let link;
        if (this.state.hover) {
            link = {color: "#a3a3a3"}
        } else {
            link = {color: "#efefef"}
        }
        return (
            <div>
                <h1 onClick={this.handleHome} onMouseEnter={this.toggleHover} onMouseLeave={this.toggleHover} style={link} className="header">Vehicle Rental</h1>
                <Menu isOpen={this.state.menuOpen} onStateChange={(state) => this.handleStateChange(state)}>
                    <a onClick={this.handleUsers} className="menu-item" href="/">Users</a>
                </Menu>
            </div>
        );
    }
}

export default withRouter(Sidebar);
