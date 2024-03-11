import React from "react";
import { Link } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";

function MainPage(props) {
  return (
    // <div className={"text-3xl"}>
    //   <div className={"flex"}>
    //     <Link to={"/about"}>About</Link>
    //   </div>
    //   <div>Main Page</div>
    // </div>
    <BasicLayout>
      <div className={"text-3xl"}>Main Page</div>
    </BasicLayout>
  );
}

export default MainPage;
