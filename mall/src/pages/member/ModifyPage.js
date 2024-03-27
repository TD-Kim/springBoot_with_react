import React from "react";
import ModifyComponent from "../../components/member/ModifyComponent";
import BasicLayout from "../../layouts/BasicLayout";

function ModifyPage() {
  return (
    <BasicLayout>
      <div className="p-4 w-full bg-white">
        <div className="text-3xl font-extrabold">Products Modify Page</div>

        <ModifyComponent />
      </div>
    </BasicLayout>
  );
}

export default ModifyPage;
