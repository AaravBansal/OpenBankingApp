import React, { useEffect, useRef, useState } from "react";
import ReactMarkdown from "react-markdown";

const MAX_MESSAGE_LENGTH = 500;
const MAX_MESSAGES = 10;

const suggestedQuestions = [
    "What accounts do I have?",
    "What is my total balance?",
    "Analyse my finances",
    "Give me financial advice"
];

// Each fresh chat starts with this assistant message. The factory avoids
// reusing a stale timestamp after a user clears the chat.
const createInitialMessage = () => ({
    sender: "ai",
    text: `👋 Hi! I'm Bank Mesh AI.

I can help you understand your accounts, balances, and financial data.

Try asking me something below.`,
    time: new Date()
});

function FinancialChat() {
    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [messages, setMessages] = useState([createInitialMessage]);
    const chatEndRef = useRef(null);

    // Keep the latest reply, loading indicator, and input area visible as the
    // conversation changes.
    useEffect(() => {
        chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages, loading]);

    const sendMessage = async (overrideMessage = null) => {
        const userText = overrideMessage || message;

        if (!userText.trim() || loading) return;

        // Mirror server-side validation so invalid messages never leave the
        // browser and the user receives immediate feedback.
        if (userText.length > MAX_MESSAGE_LENGTH) {
            setMessages((previousMessages) => [
                ...previousMessages,
                {
                    sender: "ai",
                    text: `⚠️ Your message is over the ${MAX_MESSAGE_LENGTH} character limit. Please shorten it and try again.`,
                    time: new Date()
                }
            ]);
            return;
        }

        // Limiting the transcript prevents slow responses caused by very long
        // conversations when the local AI model processes their context.
        const userMessageCount = messages.filter((item) => item.sender === "user").length;
        if (userMessageCount >= MAX_MESSAGES) {
            setMessages((previousMessages) => [
                ...previousMessages,
                {
                    sender: "ai",
                    text: "This conversation has reached its length limit. Please clear the chat and start a new conversation for the best performance.",
                    time: new Date()
                }
            ]);
            return;
        }

        // Add the message optimistically while the AI generates its response.
        setMessages((previousMessages) => [
            ...previousMessages,
            { sender: "user", text: userText, time: new Date() }
        ]);
        setMessage("");
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/api/ai/chat", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message: userText })
            });

            if (!response.ok) {
                // Validation failures return useful JSON; fall back safely for
                // unexpected server responses.
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.error || "AI request failed");
            }

            const data = await response.json();
            setMessages((previousMessages) => [
                ...previousMessages,
                { sender: "ai", text: data.reply, time: new Date() }
            ]);
        } catch (error) {
            setMessages((previousMessages) => [
                ...previousMessages,
                {
                    sender: "ai",
                    text: `⚠️ ${error.message || "Sorry, I couldn't connect to the AI service."}`,
                    time: new Date()
                }
            ]);
        } finally {
            // Always restore the input, including after a failed request.
            setLoading(false);
        }
    };

    const formatTime = (time) =>
        new Date(time).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });

    const clearChat = () => setMessages([createInitialMessage()]);

    const panelStyle = {
        position: "fixed", bottom: "95px", right: "30px", width: "400px", height: "600px",
        maxWidth: "90vw", background: "#f8fafc", borderRadius: "22px",
        boxShadow: "0 15px 50px rgba(0,0,0,0.25)", display: "flex", flexDirection: "column",
        overflow: "hidden", zIndex: 2000
    };

    return (
        <>
            {open && (
                <div style={panelStyle}>
                    {/* Chat actions and the assistant identity. */}
                    <div style={{ background: "linear-gradient(135deg,#1e90ff,#0056b3)", color: "white", padding: "18px", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                        <div>
                            <div style={{ fontSize: "18px", fontWeight: "700" }}>🤖 Bank Mesh AI</div>
                            <div style={{ fontSize: "12px", opacity: 0.85 }}>Your personal financial assistant</div>
                        </div>
                        <div>
                            <button onClick={clearChat} style={{ marginRight: "10px", background: "transparent", border: "none", color: "white", cursor: "pointer" }}>🗑</button>
                            <button onClick={() => setOpen(false)} style={{ background: "transparent", border: "none", color: "white", cursor: "pointer", fontSize: "18px" }}>✕</button>
                        </div>
                    </div>

                    {/* Markdown is rendered only for trusted AI responses, not user input. */}
                    <div style={{ flex: 1, padding: "15px", overflowY: "auto" }}>
                        {messages.map((item, index) => (
                            <div key={index} style={{ marginBottom: "15px", textAlign: item.sender === "user" ? "right" : "left" }}>
                                <div style={{ display: "inline-block", maxWidth: "85%", padding: "12px 16px", borderRadius: item.sender === "user" ? "18px 18px 5px 18px" : "18px 18px 18px 5px", background: item.sender === "user" ? "#1e90ff" : "white", color: item.sender === "user" ? "white" : "#111827", boxShadow: "0 3px 10px rgba(0,0,0,0.08)", textAlign: "left" }}>
                                    {item.sender === "ai" ? <ReactMarkdown>{item.text}</ReactMarkdown> : item.text}
                                    <div style={{ fontSize: "10px", opacity: 0.6, marginTop: "6px" }}>{formatTime(item.time)}</div>
                                </div>
                            </div>
                        ))}
                        {loading && (
                            <div style={{ background: "white", padding: "12px", borderRadius: "15px", width: "100px", boxShadow: "0 3px 10px rgba(0,0,0,0.08)" }}>
                                Thinking <span className="dots">...</span>
                            </div>
                        )}
                        <div ref={chatEndRef} />
                    </div>

                    {/* Suggestions are available only before the first message. */}
                    {messages.length === 1 && (
                        <div style={{ padding: "10px" }}>
                            {suggestedQuestions.map((question) => (
                                <button key={question} onClick={() => sendMessage(question)} style={{ margin: "4px", padding: "8px 12px", borderRadius: "20px", border: "1px solid #ddd", background: "white", cursor: "pointer", fontSize: "12px" }}>
                                    {question}
                                </button>
                            ))}
                        </div>
                    )}

                    <div style={{ display: "flex", flexDirection: "column", padding: "12px", background: "white", borderTop: "1px solid #ddd" }}>
                        {/* Shows remaining capacity before server-side validation runs. */}
                        <div style={{ fontSize: "11px", textAlign: "right", marginBottom: "4px", color: message.length > MAX_MESSAGE_LENGTH ? "#e11d48" : "#999" }}>
                            {MAX_MESSAGE_LENGTH - message.length < 0 ? `${message.length - MAX_MESSAGE_LENGTH} characters over limit` : `${MAX_MESSAGE_LENGTH - message.length} characters remaining`}
                        </div>
                        <div style={{ display: "flex" }}>
                            <input value={message} disabled={loading} onChange={(event) => setMessage(event.target.value)} onKeyDown={(event) => { if (event.key === "Enter") sendMessage(); }} placeholder="Ask about your finances..." style={{ flex: 1, borderRadius: "20px", border: "1px solid #ccc", padding: "12px", outline: "none" }} />
                            <button disabled={loading} onClick={() => sendMessage()} style={{ marginLeft: "8px", width: "45px", height: "45px", borderRadius: "50%", border: "none", background: loading ? "#aaa" : "#1e90ff", color: "white", cursor: "pointer" }}>➤</button>
                        </div>
                    </div>
                </div>
            )}

            {/* The floating control keeps chat available from any screen. */}
            <button onClick={() => setOpen(!open)} style={{ position: "fixed", bottom: "25px", right: "30px", width: "65px", height: "65px", borderRadius: "50%", border: "none", background: "#1e90ff", color: "white", fontSize: "28px", cursor: "pointer", boxShadow: "0 8px 25px rgba(0,0,0,0.3)", zIndex: 2001 }}>💬</button>
        </>
    );
}

export default FinancialChat;
