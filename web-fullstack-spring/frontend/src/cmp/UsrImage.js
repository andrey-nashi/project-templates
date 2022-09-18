import React, { Component } from 'react'
import { Header, Table, Button } from 'semantic-ui-react'
import axios from 'axios'

import Settings from '../settings/Settings'

const Example = ({ data }) => <img src={`data:image/png;base64,${data}`} style={{width: "100px", height: "100px"}}/>

class UsrImage extends Component {

    //-------------------------------------
    fetchImage() {
        var URL = Settings.API_USR_IMAGE + "/" + this.props.id
        console.log(URL)
        axios.get(URL).then(response => {
        console.log(response.data)
        this.setState({data:response.data})})
    }

    //-------------------------------------

    constructor(props) {
        super(props)
        var tck = sessionStorage.getItem("TOKEN")
        axios.interceptors.request.use((config) => {
                config.headers.authorization = tck;
                return config;
            }
        )

        this.state = {
            data: 0
        }
    }

    componentDidMount() {
        this.fetchImage()
    }


    render() {
        return (
            <div>
            <Example data={this.state.data} />
            </div>
        )
    }

}

export default UsrImage;