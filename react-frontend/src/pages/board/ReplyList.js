import React, { useEffect, useState } from 'react';
import BoardItem from '../../components/BoardItem';
import Paging from '../../components/Paging';
import { useLocation } from 'react-router-dom';
const ReplyList = () => {
  const [posts, setPosts] = useState([]);
  const [no, setNo] = useState(0);
  const [count, setCount] = useState('');
  const location = useLocation();
  const originNo = location.state.id;
  const b_layer = location.state.layer;
  const layer = b_layer + 1;

  useEffect(() => {
    fetch('http://localhost:8080/reply/' + originNo + '/' + layer)
      .then((res) => res.json())
      .then((res) => {
        setPosts(res.content);
        setCount(res.totalElements);
      }); //비동기 함수
  }, [layer]);

  useEffect(() => {
    setNo(0);
  }, []);

  return (
    <div>
      {posts.map((post) => (
        <BoardItem key={post.id} post={post} />
      ))}
      <Paging setNo={setNo} count={count} />
    </div>
  );
};

export default ReplyList;
