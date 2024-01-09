import React, { useEffect, useState } from 'react';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import UserService from '../Services/userService';

var stompClient = null;

const ChatRoom = () => {

    const [privateChats, setPrivateChats] = useState(new Map());
    const [publicChats, setPublicChats] = useState([]);
    const [tab, setTab] = useState("CHATROOM");
    const [nick, setNick] = useState("");
    const [res, setRes] = useState({
        name:""
    }) 
    const [userData, setUserData] = useState({
        name: "",
        receivername: '',
        connected: false,
        message: '',
        email: '',
        password: ''
    });
    
    const connect = () => {
        let Sock = new SockJS('http://localhost:8093/ws');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }

    const onConnected = () => {
        setUserData({ ...userData, "connected": true });
        stompClient.subscribe('/chatroom/public', onMessageReceived);
        stompClient.subscribe('/user/' + userData.name + '/private', onPrivateMessage);
        userJoin();
        
    }

    const userJoin = () => {
        var chatMessage = {
            senderName: userData.name,
            status: "JOIN"
        };
       

        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
        
    }

    const onMessageReceived = (payload) => {
        var payloadData = JSON.parse(payload.body);
        switch (payloadData.status) {
            case "JOIN":
                if (!privateChats.get(payloadData.senderName)) {
                    privateChats.set(payloadData.senderName, []);
                    setPrivateChats(new Map(privateChats));
                    
                }
                break;
            case "MESSAGE":
                publicChats.push(payloadData);
                setPublicChats([...publicChats]);
                break;
        }
    }

    const onPrivateMessage = (payload) => {
        console.log(payload);
        var payloadData = JSON.parse(payload.body);
        if (privateChats.get(payloadData.senderName)) {
            privateChats.get(payloadData.senderName).push(payloadData);
            setPrivateChats(new Map(privateChats));
        } else {
            let list = [];
            list.push(payloadData);
            privateChats.set(payloadData.senderName, list);
            setPrivateChats(new Map(privateChats));
        }
    }

    const onError = (err) => {
        console.log(err);
    }

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
    }

    const sendValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.name,
                message: userData.message,
                status: "MESSAGE"
            };
            console.log(chatMessage);
            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            setUserData({ ...userData, "message": "" });
        }
    }

    const sendPrivateValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.name,
                receiverName: tab,
                message: userData.message,
                status: "MESSAGE"
            };

            if (userData.name !== tab) {
                privateChats.get(tab).push(chatMessage);
                setPrivateChats(new Map(privateChats));
            }
            stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
            setUserData({ ...userData, "message": "" });
        }
    }

    const handleEmail = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "email": value });
    }

    const handlePassword = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "password": value });
    }

    const registerUser = () => {
        const userService = new UserService();
        userService.loginUser(userData.email, userData.password)
            .then(response => {
               
                if (response.success === true) {
                    alert("Giriş başarılı");
                    setRes({name:response.data.name})
                    alert(res.name)
                   
                   
                    connect();
                } else {
                    alert(response.message);
                }
            })
            .catch(result => {
                console.log(result);
            });
    }

    return (
        <div className="container">
            {userData.connected ?
                <div className="chat-box">
                    <div className="member-list">
                        <ul>
                            <li onClick={() => { setTab("CHATROOM") }} className={`member ${tab === "CHATROOM" && "active"}`}>Chatroom</li>
                            {[...privateChats.keys()].map((name, index) => (
                                <li onClick={() => { setTab(name) }} className={`member ${tab === name && "active"}`} key={index}>{name}</li>
                            ))}
                        </ul>
                    </div>
                    {tab === "CHATROOM" && <div className="chat-content">
                        <ul className="chat-messages">
                            {publicChats.map((chat, index) => (
                                <li className={`message ${chat.senderName === userData.name && "self"}`} key={index}>
                                    {chat.senderName !== userData.name && <div className="avatar">{chat.senderName}</div>}
                                    <div className="message-data">{chat.message}</div>
                                    {chat.senderName === userData.name && <div className="avatar self">{chat.senderName}</div>}
                                </li>
                            ))}
                        </ul>

                        <div className="send-message">
                            <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
                            <button type="button" className="send-button" onClick={sendValue}>send</button>
                        </div>
                    </div>}
                    {tab !== "CHATROOM" && <div className="chat-content">
                        <ul className="chat-messages">
                            {[...privateChats.get(tab)].map((chat, index) => (
                                <li className={`message ${chat.senderName === userData.name && "self"}`} key={index}>
                                    {chat.senderName !== userData.name && <div className="avatar">{chat.senderName}</div>}
                                    <div className="message-data">{chat.message}</div>
                                    {chat.senderName === userData.name && <div className="avatar self">{chat.senderName}</div>}
                                </li>
                            ))}
                        </ul>

                        <div className="send-message">
                            <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
                            <button type="button" className="send-button" onClick={sendPrivateValue}>send</button>
                        </div>
                    </div>}
                </div>
                :
                <div className="register">
                    <input
                        id="e-mail"
                        placeholder="Enter your e-mail"
                        name="email"
                        value={userData.email}
                        onChange={handleEmail}
                        margin="normal"
                    />
                    <input
                        id="password"
                        placeholder="Enter your password"
                        name="password"
                        value={userData.password}
                        onChange={handlePassword}
                        margin="normal"
                    />
                    <button type="button" onClick={registerUser}>
                        connect
                    </button>
                </div>}
        </div>
    )
}

export default ChatRoom;
