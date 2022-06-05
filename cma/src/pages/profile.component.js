import React, { Component } from "react";
import articleService from "../services/articleService";
import userService from "../services/user.service";
import Itemcard from "./components/item_card";

export default class Profile extends Component {
  state = {
    user:null,
    articles:[]
  };

  componentDidMount() {
    this.init();
  }

  async init() {
    this.getMyArticles();
    const {data} = await userService.getUserBoard();
    console.log(data);
    this.setState({ user: data });
  }

  async getMyArticles(){
    const {data} = await articleService.getArticleByUser();
    console.log("my articles",data);
    this.setState({articles:data});
  }

  render() {
    const { user } = this.state;
    return (
      <div className="container">
        <h1 className="mt-2 mb-6 mw-xl">Your articles</h1>
        <div className="row align-items-center">
          <div className="row p-5">
            {this.state.articles.map((e, i) => (
              <div key={i} className="col-md-6 align-items-center">
                <Itemcard article={e} />
              </div>
            ))}
          </div>
        </div>
      </div>
    );
  }
}
