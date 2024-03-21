import React, { useState } from "react";
import ResultModal from "../common/ResultModal";
import { postAdd } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
  title: "",
  writer: "",
  dueDate: "",
};

function AddComponent(props) {
  const [todo, setTodo] = useState({ ...initState });
  const [result, setResult] = useState(null);
  const { moveToList } = useCustomMove();

  const handleChangeTodo = (e) => {
    todo[e.target.name] = e.target.value;

    setTodo({ ...todo });
  };

  const handleClickAdd = () => {
    // console.log(todo);
    postAdd(todo).then((result) => {
      setResult(result.TNO);
      setTodo({ ...initState }); // 등록 후 input 태그 내용 지우기
    });
  };

  const closeModal = () => {
    setResult(null);
    moveToList();
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">TITLE</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="title"
            type="text"
            value={todo.title}
            onChange={handleChangeTodo}
          />
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">WRTIER</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="writer"
            type="text"
            value={todo.writer}
            onChange={handleChangeTodo}
          />
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">DUEDATE</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="dueDate"
            type="date"
            value={todo.dueDate}
            onChange={handleChangeTodo}
          />
        </div>
      </div>
      <div className="flex">
        <div className="relative mb-4 flex p-4 w-full justify-end flex-wrap items-stretch">
          <button
            type="button"
            className="rounded p-4 w-36 text-xl text-white bg-blue-500"
            onClick={handleClickAdd}
          >
            ADD
          </button>
        </div>
      </div>
      {result ? (
        <ResultModal
          title={"Add Result"}
          content={`New ${result} Added`}
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}
    </div>
  );
}

export default AddComponent;
