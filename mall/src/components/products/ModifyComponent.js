import React, { useEffect, useRef, useState } from "react";
import {
  API_SERVER_HOST,
  deleteOne,
  getOne,
  putOne,
} from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

const initState = {
  pno: 0,
  pname: "",
  pdesc: "",
  price: 0,
  delFlag: false,
  uploadFileNames: [],
};

const host = API_SERVER_HOST;

function ModifyComponent({ pno }) {
  const [product, setProduct] = useState(initState);
  const [fetching, setFetching] = useState(false);
  const [result, setResult] = useState(false);

  const { moveToList, moveToRead } = useCustomMove();

  const uploadRef = useRef();

  const delMutation = useMutation({ mutationFn: (pno) => deleteOne(pno) });
  const modMutation = useMutation({
    mutationFn: (product) => putOne(pno, product),
  });

  // useEffect(() => {
  //   setFetching(true);

  //   getOne(pno).then((data) => {
  //     setProduct(data);
  //     setFetching(false);
  //   });
  // }, []);

  const query = useQuery({
    queryKey: ["products", pno],
    queryFn: () => getOne(pno),
    staleTime: 1000 * 10,
  });

  useEffect(() => {
    if (query.isSuccess) {
      setProduct(query.data);
    }
  }, [pno, query.data, query.isSuccess]);

  const handleChangeProduct = (e) => {
    product[e.target.name] = e.target.value;
    setProduct({ ...product });
  };

  const handleClickModify = (e) => {
    const files = uploadRef.current.files;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    formData.append("pname", product.pname);
    formData.append("pdesc", product.pdesc);
    formData.append("price", product.price);
    formData.append("delFlag", product.delFlag);

    for (let i = 0; i < product.uploadFileNames.length; i++) {
      formData.append("uploadFileNames", product.uploadFileNames[i]);
    }

    setFetching(true);

    // putOne(pno, formData).then((data) => {
    //   setResult("Modified");
    //   setFetching(false);
    // });

    modMutation.mutate(formData);
  };

  const handleClickDelete = () => {
    // setFetching(true);
    // deleteOne(pno).then((data) => {
    //   setResult("Deleted");
    //   setFetching(false);
    // });

    delMutation.mutate(pno);
  };

  const queryClient = useQueryClient();

  const closeModal = () => {
    // if (result === "Modified") {
    //   moveToRead(pno);
    // } else if (result === "Deleted") {
    //   moveToList({ page: 1 });
    // }
    // setResult(null);

    queryClient.invalidateQueries(["products", pno]);
    queryClient.invalidateQueries("products/list");

    if (delMutation.isSuccess) {
      moveToList();
    }

    if (modMutation.isSuccess) {
      moveToRead(pno);
    }
  };

  const deleteOldImages = (imageName) => {
    const resultFileNames = product.uploadFileNames.filter(
      (fileName) => fileName !== imageName
    );

    product.uploadFileNames = resultFileNames;
    setProduct({ ...product });
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {/* {fetching ? <FetchingModal /> : <></>} */}
      {query.isFetching || delMutation.isPending || modMutation.isPending ? (
        <FetchingModal />
      ) : (
        <></>
      )}

      {/* {result ? (
        <ResultModal
          title={`${result}`}
          content={"정상적으로 처리되었습니다."} //결과 모달창
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )} */}
      {delMutation.isSuccess || modMutation.isSuccess ? (
        <ResultModal
          title={`처리결과`}
          content={"정상적으로 처리되었습니다."} //결과 모달창
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Product Name</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            name="pname"
            type={"text"}
            value={product.pname}
            onChange={handleChangeProduct}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Desc</div>
          <textarea
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
            name="pdesc"
            rows="4"
            onChange={handleChangeProduct}
            value={product.pdesc}
          >
            {product.pdesc}
          </textarea>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Price</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            name="price"
            type={"number"}
            value={product.price}
            onChange={handleChangeProduct}
          ></input>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">DELETE</div>
          <select
            name="delFlag"
            value={product.delFlag}
            onChange={handleChangeProduct}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
          >
            <option value={false}>사용</option>
            <option value={true}>삭제</option>
          </select>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Files</div>
          <input
            ref={uploadRef}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            type={"file"}
            multiple={true}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Images</div>
          <div className="w-4/5 justify-center flex flex-wrap items-start">
            {product.uploadFileNames.map((imgFile, i) => (
              <div className="flex justify-center flex-col w-1/3" key={i}>
                <button
                  className="bg-blue-500 text-3xl text-white"
                  onClick={() => deleteOldImages(imgFile)}
                >
                  DELETE
                </button>
                <img alt="img" src={`${host}/api/products/view/s_${imgFile}`} />
              </div>
            ))}
          </div>
        </div>
      </div>

      <div className="flex justify-end p-4">
        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-red-500"
          onClick={handleClickDelete}
        >
          Delete
        </button>

        <button
          type="button"
          className="inline-block rounded p-4 m-2 text-xl w-32  text-white bg-orange-500"
          onClick={handleClickModify}
        >
          Modify
        </button>

        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={moveToList}
        >
          List
        </button>
      </div>
    </div>
  );
}

export default ModifyComponent;
