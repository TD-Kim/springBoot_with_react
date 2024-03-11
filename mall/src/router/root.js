import { Suspense, lazy } from "react";
import todoRouter from "./todoRouter";

const { createBrowserRouter } = require("react-router-dom");

// 코드 스플릿팅
const Loading = <div className={"bg-red-700"}>Loading...</div>;
const Main = lazy(() => import("../pages/MainPage")); // 필요할 때만 MainPage 를 실행해라
const About = lazy(() => import("../pages/AboutPage"));
const TotoIndex = lazy(() => import("../pages/todo/IndexPage"));
// const TodoList = lazy(() => import("../pages/todo/ListPage"));

const root = createBrowserRouter([
  {
    path: "",
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: "about",
    element: (
      <Suspense fallback={Loading}>
        <About />
      </Suspense>
    ),
  },
  {
    path: "todo",
    element: (
      <Suspense fallback={Loading}>
        <TotoIndex />
      </Suspense>
    ),
    children: todoRouter(),
  },
]);

export default root;
