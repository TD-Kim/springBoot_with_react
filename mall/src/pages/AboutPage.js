import React from "react";
import { Link } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";

function AboutPage(props) {
  return (
    // <div className={"text-3xl"}>
    //   <div className={"flex"}>
    //     <Link to={"/about"}>About</Link>
    //   </div>
    //   <div>About Page</div>
    // </div>
    <BasicLayout>
      <div className={"text-3xl"}>About Page</div>
    </BasicLayout>
  );
}

export default AboutPage;
