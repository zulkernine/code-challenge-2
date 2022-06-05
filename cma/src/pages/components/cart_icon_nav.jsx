import React, { Component } from "react";
import articleIcon from "../../assets/icons/icons8-article-64.png";


class CartIconComponent extends Component {
  

  render() {
    return (
      <div>
        <a
          className="link-dark me-12 text-decoration-none d-flex align-items-center"
          href="/cart"
        >
          <img src={articleIcon} height="32" width="32" />;
        </a>
      </div>
    );
  }
}

export default CartIconComponent;
