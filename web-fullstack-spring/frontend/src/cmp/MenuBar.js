import React, { Component } from 'react'
import { Menu } from 'semantic-ui-react'

class MenuBar extends Component {

    render() {
        return (
        <Menu>
            <Menu.Item name="home"><a href="/home">Home</a></Menu.Item>
            <Menu.Item name="login"><a href="/login">Login</a></Menu.Item>
            <Menu.Item name="logout"><a href="/logout">Logout</a></Menu.Item>
        </Menu>
                );

    }
}

export default MenuBar;