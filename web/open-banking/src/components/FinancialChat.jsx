import React, { useState, useEffect, useRef } from "react";

function FinancialChat() {

    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);

    const chatEndRef = useRef(null);


    const [messages, setMessages] = useState([
        {
            sender: "ai",
            text: "Hi! I'm your Bank Mesh AI assistant. Ask me anything about your accounts."
        }
    ]);


    useEffect(() => {

        chatEndRef.current?.scrollIntoView({
            behavior: "smooth"
        });

    }, [messages]);



    const sendMessage = async () => {

        if (!message.trim() || loading) return;


        const userText = message;


        setMessages(prev => [
            ...prev,
            {
                sender:"user",
                text:userText,
                time:new Date().toLocaleTimeString()
            }
        ]);


        setMessage("");
        setLoading(true);


        try {

            const response = await fetch(
                "http://localhost:8080/api/ai/chat",
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify({
                        message:userText
                    })
                }
            );


            const data = await response.json();


            setMessages(prev => [
                ...prev,
                {
                    sender:"ai",
                    text:data.reply,
                    time:new Date().toLocaleTimeString()
                }
            ]);


        } catch(error) {


            setMessages(prev => [
                ...prev,
                {
                    sender:"ai",
                    text:"Sorry, I couldn't connect to the AI service.",
                    time:new Date().toLocaleTimeString()
                }
            ]);

        }


        setLoading(false);

    };



    return (

        <>

            {open && (

                <div
                    style={{
                        position:"fixed",
                        bottom:"90px",
                        right:"30px",
                        width:"380px",
                        height:"550px",
                        background:"#f8fafc",
                        borderRadius:"20px",
                        boxShadow:"0 10px 40px rgba(0,0,0,0.25)",
                        display:"flex",
                        flexDirection:"column",
                        overflow:"hidden",
                        zIndex:1000
                    }}
                >


                    {/* Header */}

                    <div
                        style={{
                            padding:"18px",
                            background:"#1e90ff",
                            color:"white",
                            fontWeight:"700",
                            display:"flex",
                            justifyContent:"space-between"
                        }}
                    >

                    <span>
                        🤖 Bank Mesh AI
                    </span>


                        <span
                            style={{
                                cursor:"pointer"
                            }}
                            onClick={() => setOpen(false)}
                        >
                        ✕
                    </span>

                    </div>



                    {/* Messages */}

                    <div
                        style={{
                            flex:1,
                            padding:"15px",
                            overflowY:"auto"
                        }}
                    >


                        {messages.map((msg,index)=>(

                            <div
                                key={index}
                                style={{
                                    marginBottom:"15px",
                                    textAlign:
                                        msg.sender==="user"
                                            ?"right"
                                            :"left"
                                }}
                            >


                                <div
                                    style={{
                                        display:"inline-block",
                                        maxWidth:"80%",
                                        padding:"12px 15px",
                                        borderRadius:"18px",
                                        background:
                                            msg.sender==="user"
                                                ? "#1e90ff"
                                                :"white",
                                        color:
                                            msg.sender==="user"
                                                ?"white"
                                                :"#111",
                                        boxShadow:"0 2px 8px rgba(0,0,0,0.08)",
                                        whiteSpace:"pre-wrap"
                                    }}
                                >

                                    {msg.text}


                                </div>


                            </div>

                        ))}



                        {loading && (

                            <div
                                style={{
                                    color:"#666",
                                    fontStyle:"italic"
                                }}
                            >

                                AI is thinking • • •

                            </div>

                        )}


                        <div ref={chatEndRef}/>


                    </div>



                    {/* Input */}

                    <div
                        style={{
                            display:"flex",
                            padding:"12px",
                            background:"white",
                            borderTop:"1px solid #ddd"
                        }}
                    >

                        <input

                            value={message}

                            disabled={loading}

                            onChange={(e)=>
                                setMessage(e.target.value)
                            }

                            onKeyDown={(e)=>{
                                if(e.key==="Enter"){
                                    sendMessage();
                                }
                            }}

                            placeholder="Ask about your finances..."

                            style={{
                                flex:1,
                                borderRadius:"20px",
                                border:"1px solid #ccc",
                                padding:"12px",
                                outline:"none"
                            }}

                        />


                        <button

                            onClick={sendMessage}

                            disabled={loading}

                            style={{
                                marginLeft:"8px",
                                borderRadius:"50%",
                                width:"45px",
                                border:"none",
                                background:"#1e90ff",
                                color:"white",
                                cursor:"pointer"
                            }}

                        >

                            ➤

                        </button>


                    </div>


                </div>

            )}



            <button

                onClick={()=>setOpen(!open)}

                style={{
                    position:"fixed",
                    bottom:"25px",
                    right:"30px",
                    width:"65px",
                    height:"65px",
                    borderRadius:"50%",
                    border:"none",
                    background:"#1e90ff",
                    color:"white",
                    fontSize:"28px",
                    cursor:"pointer",
                    boxShadow:"0 5px 15px rgba(0,0,0,0.25)",
                    zIndex:1001
                }}

            >

                💬

            </button>


        </>

    );
}


export default FinancialChat;