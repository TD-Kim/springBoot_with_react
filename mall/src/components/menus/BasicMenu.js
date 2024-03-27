import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { logout } from "../../slices/loginSlice";
import useCustomLogin from "../../hooks/useCustomLogin";

function BasicMenu() {
  const loginState = useSelector((state) => state.loginSlice); // 어느 컴포넌트든 useSelect 를 쓰면 state 가 바뀌는것을 알 수 있다.

  const dispatch = useDispatch();

  const { doLogout, moveToPath } = useCustomLogin();

  const handleTest = () => {
    doLogout();
    alert("로그아웃 되었습니다.");
    moveToPath("/");
  };

  console.log("loginState.......");

  return (
    <nav id="navbar" className=" flex bg-blue-300">
      <div className="w-4/5 bg-gray-500">
        <ul className="flex p-4 text-white font-bold">
          <li className="pr-6 text-2xl">
            <Link to={"/"}>Main</Link>
          </li>
          <li className="pr-6 text-2xl">
            <Link to={"/about"}>About</Link>
          </li>
          {loginState.email ? (
            <>
              <li className="pr-6 text-2xl">
                <Link to={"/todo/"}>Todo</Link>
              </li>
              <li className="pr-6 text-2xl">
                <Link to={"/products/"}>Products</Link>
              </li>
            </>
          ) : (
            <></>
          )}
        </ul>
      </div>

      <div className="w-1/5 flex justify-end bg-orange-300 p-4 font-medium">
        {/* <div className="text-white text-sm m-1 rounded">
          <Link to={"/member/login"}>Login</Link>
        </div> */}
        {!loginState.email ? (
          <div className="text-white text-sm m-1 rounded">
            <Link to={"/member/login"}>Login</Link>
          </div>
        ) : (
          // <div className="text-white text-sm m-1 rounded">
          //   <Link to={"/member/logout"}>Logout</Link>
          // </div>
          <div className="text-white text-sm m-1 rounded">
            <Link onClick={handleTest}>Logout</Link>
          </div>
        )}
      </div>
    </nav>
  );
}

export default BasicMenu;
