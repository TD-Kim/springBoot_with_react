import React from "react";
import BasicMenu from "../components/menus/BasicMenu";

// children props 를 사용하여 화면의 전체 구성을 잡는 컴포넌트
function BasicLayout({ children }) {
  return (
    <>
      {/* <header className="bg-teal-400 p-5">
        <h1 className="text-2xl md:text-4xl">
          <BasicMenu />
        </h1>
      </header> */}
      <BasicMenu />

      {/* <div className="bg-white my-5 w-full flex flex-col space-y-4 md:flex-row md:space-x-4 md:space-y-0">
        <main className="bg-sky-300 md:w-2/3 lg:w-3/4 px-5 py-40">
          {children}
        </main>
        <aside className="bg-green-300 md:w-1/3 lg:w-1/4 px-5 py-40">
          <h1 className="text-2xl md:text-4xl">Sidebar</h1>
        </aside>
      </div> */}

      <div className="bg-white my-5 w-full flex flex-col space-y-1 md:flex-row md:space-x-1 md:space-y-0">
        <main className="bg-sky-300 md:w-4/5 lg:w-3/4 px-5 py-5">
          {children}
        </main>
        <aside className="bg-green-300 md:w-1/5 lg:w-1/4 px-5 flex py-5">
          <h1 className="text-2xl md:text-4xl">Sidebar</h1>
        </aside>
      </div>
    </>
  );
}

export default BasicLayout;
