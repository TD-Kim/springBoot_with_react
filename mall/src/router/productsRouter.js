import React, { Suspense, lazy } from "react";
import { Navigate } from "react-router-dom";

const Loading = <div className={"bg-red-700"}>Loading...</div>;
const ProductList = lazy(() => import("../pages/products/ListPage"));
const ProductAdd = lazy(() => import("../pages/products/AddPage"));
const ProductRead = lazy(() => import("../pages/products/ReadPage"));
const ProductModify = lazy(() => import("../pages/products/ModifyPage"));

const productsRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={Loading}>
          <ProductList></ProductList>
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to={"/products/list"}></Navigate>, // replace 속성으로 뒤로가기 막을 수 있음
    },
    {
      path: "add",
      element: (
        <Suspense fallback={Loading}>
          <ProductAdd></ProductAdd>
        </Suspense>
      ),
    },
    {
      path: "read/:pno",
      element: (
        <Suspense fallback={Loading}>
          <ProductRead></ProductRead>
        </Suspense>
      ),
    },
    {
      path: "modify/:pno",
      element: (
        <Suspense fallback={Loading}>
          <ProductModify></ProductModify>
        </Suspense>
      ),
    },
  ];
};

export default productsRouter;
