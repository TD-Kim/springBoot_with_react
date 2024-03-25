import React from "react";
import BasicMenu from "../../components/menus/BasicMenu";
import LogoutComponent from "../../components/member/LogoutComponent";

function LogoutPage(props) {
  return (
    <div className="fixed top-0 left-0 z-[1055] flex flex-col h-full w-full">
      <BasicMenu />

      <div className="w-full flex flex-wrap  h-full justify-center  items-center border-2">
        <LogoutComponent></LogoutComponent>
      </div>
    </div>
  );
}

export default LogoutPage;
