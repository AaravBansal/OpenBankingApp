import React, { useState, useEffect, useRef } from "react";
import ReactMarkdown from "react-markdown";


function FinancialChat() {

    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);


    const chatEndRef = useRef(null);


    const suggestedQuestions = [
        "What accounts do I have?",
        "What is my total balance?",
        "Analyse my finances",
        "Give me financial advice"
    ];



    const initialMessage = {
        sender: "ai",
        text:
            `👋 Hi! I'm Bank Mesh AI.

I can help you understand your accounts, balances, and financial data.

Try asking me something below.`,
        time: new Date()
    };



    const [messages, setMessages] = useState([
        initialMessage
    ]);



    useEffect(() => {

        chatEndRef.current?.scrollIntoView({
            behavior:"smooth"
        });

    }, [messages, loading]);





    const sendMessage = async (overrideMessage=null) => {


        const userText =
            overrideMessage || message;


        if(!userText.trim() || loading)
            return;



        setMessages(prev => [
            ...prev,
            {
                sender:"user",
                text:userText,
                time:new Date()
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



            if(!response.ok){
                throw new Error("AI request failed");
            }



            const data = await response.json();



            setMessages(prev => [
                ...prev,
                {
                    sender:"ai",
                    text:data.reply,
                    time:new Date()
                }
            ]);



        }
        catch(error){


            setMessages(prev => [
                ...prev,
                {
                    sender:"ai",
                    text:
                        "⚠️ Sorry, I couldn't connect to the AI service.",
                    time:new Date()
                }
            ]);

        }



        setLoading(false);

    };





    const formatTime = (time)=>{

        return new Date(time)
            .toLocaleTimeString([],{
                hour:"2-digit",
                minute:"2-digit"
            });

    };





    const clearChat = ()=>{

        setMessages([
            initialMessage
        ]);

    };





    return (

        <>


            {open && (


                <div

                    style={{

                        position:"fixed",

                        bottom:"95px",

                        right:"30px",

                        width:"400px",

                        height:"600px",

                        maxWidth:"90vw",

                        background:"#f8fafc",

                        borderRadius:"22px",

                        boxShadow:
                            "0 15px 50px rgba(0,0,0,0.25)",

                        display:"flex",

                        flexDirection:"column",

                        overflow:"hidden",

                        zIndex:2000

                    }}

                >




                    {/* HEADER */}

                    <div

                        style={{

                            background:
                                "linear-gradient(135deg,#1e90ff,#0056b3)",

                            color:"white",

                            padding:"18px",

                            display:"flex",

                            justifyContent:"space-between",

                            alignItems:"center"

                        }}

                    >


                        <div>

                            <div
                                style={{
                                    fontSize:"18px",
                                    fontWeight:"700"
                                }}
                            >
                                🤖 Bank Mesh AI
                            </div>


                            <div
                                style={{
                                    fontSize:"12px",
                                    opacity:0.85
                                }}
                            >
                                Your personal financial assistant
                            </div>

                        </div>



                        <div>


                            <button

                                onClick={clearChat}

                                style={{

                                    marginRight:"10px",

                                    background:"transparent",

                                    border:"none",

                                    color:"white",

                                    cursor:"pointer"

                                }}

                            >
                                🗑
                            </button>



                            <button

                                onClick={()=>setOpen(false)}

                                style={{

                                    background:"transparent",

                                    border:"none",

                                    color:"white",

                                    cursor:"pointer",

                                    fontSize:"18px"

                                }}

                            >

                                ✕

                            </button>


                        </div>



                    </div>





                    {/* CHAT AREA */}


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

                                        maxWidth:"85%",

                                        padding:"12px 16px",

                                        borderRadius:
                                            msg.sender==="user"
                                                ?"18px 18px 5px 18px"
                                                :"18px 18px 18px 5px",


                                        background:

                                            msg.sender==="user"

                                                ?

                                                "#1e90ff"

                                                :

                                                "white",


                                        color:

                                            msg.sender==="user"

                                                ?

                                                "white"

                                                :

                                                "#111827",


                                        boxShadow:
                                            "0 3px 10px rgba(0,0,0,0.08)",


                                        textAlign:"left"

                                    }}

                                >



                                    {msg.sender==="ai" ? (

                                        <ReactMarkdown>
                                            {msg.text}
                                        </ReactMarkdown>

                                    ):(

                                        msg.text

                                    )}




                                    <div

                                        style={{

                                            fontSize:"10px",

                                            opacity:0.6,

                                            marginTop:"6px"

                                        }}

                                    >

                                        {formatTime(msg.time)}

                                    </div>



                                </div>



                            </div>


                        ))}




                        {loading && (

                            <div

                                style={{

                                    background:"white",

                                    padding:"12px",

                                    borderRadius:"15px",

                                    width:"100px",

                                    boxShadow:
                                        "0 3px 10px rgba(0,0,0,0.08)"

                                }}

                            >

                                Thinking
                                <span className="dots">
                ...
            </span>


                            </div>

                        )}



                        <div ref={chatEndRef}/>


                    </div>







                    {/* SUGGESTIONS */}

                    {messages.length === 1 && (

                        <div

                            style={{

                                padding:"10px"

                            }}

                        >


                            {suggestedQuestions.map((q,index)=>(


                                <button

                                    key={index}

                                    onClick={()=>sendMessage(q)}

                                    style={{

                                        margin:"4px",

                                        padding:"8px 12px",

                                        borderRadius:"20px",

                                        border:"1px solid #ddd",

                                        background:"white",

                                        cursor:"pointer",

                                        fontSize:"12px"

                                    }}

                                >

                                    {q}

                                </button>


                            ))}


                        </div>

                    )}




                    {/* INPUT */}


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

                            onChange={(e)=>setMessage(e.target.value)}


                            onKeyDown={(e)=>{

                                if(e.key==="Enter")
                                    sendMessage();

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

                            disabled={loading}

                            onClick={()=>sendMessage()}


                            style={{

                                marginLeft:"8px",

                                width:"45px",

                                height:"45px",

                                borderRadius:"50%",


                                border:"none",

                                background:
                                    loading
                                        ?
                                        "#aaa"
                                        :
                                        "#1e90ff",

                                color:"white",

                                cursor:"pointer"

                            }}

                        >

                            ➤

                        </button>


                    </div>




                </div>


            )}






            {/* FLOAT BUTTON */}


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

                    boxShadow:
                        "0 8px 25px rgba(0,0,0,0.3)",

                    zIndex:2001

                }}

            >

                💬

            </button>


        </>

    );

}


export default FinancialChat;