import React, { useState } from 'react';
import {
    Paper,
    Typography,
    TextField,
    Button,
    Box,
    Divider,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function LoginPage({ setGoogleUser }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    // REMOVED the useEffect for Google OAuth redirect

    const handleHardcodedLogin = async (e) => {
        e.preventDefault();
        e.stopPropagation();
        console.log('🚀 Login form submitted');

        try {
            const res = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ username, password }),
                credentials: 'include',
            });

            if (res.ok) {
                navigate('/dashboard');
            } else {
                const data = await res.json().catch(() => ({}));
                alert(data.message || 'Login failed: Invalid credentials');
            }
        } catch (err) {
            alert('Login error: ' + err.message);
        }
    };

    return (
        <Box
            sx={{
                height: '100vh',
                background: 'linear-gradient(to right, #0a5e96, #377ba9)',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                px: 2,
            }}
        >
            <Paper
                elevation={6}
                sx={{
                    maxWidth: 420,
                    width: '100%',
                    borderRadius: 4,
                    px: 3,
                    py: 3,
                    textAlign: 'center',
                }}
            >
                <img
                    src="/bankmesh-logo.png"
                    alt="BankMesh Logo"
                    style={{ width: '100%', maxWidth: 260, borderRadius: '16px', marginBottom: 16 }}
                />

                <Typography variant="h4" fontWeight="700" gutterBottom>
                    Welcome
                </Typography>
                <Typography variant="body2" sx={{ mb: 2 }}>
                    Sign in to your account
                </Typography>

                {/* Use a real <form> here */}
                <form onSubmit={handleHardcodedLogin} style={{ width: '100%' }}>
                    <TextField
                        label="Email Address"
                        fullWidth
                        size="small"
                        margin="dense"
                        required
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        sx={{ mb: 1.5 }}
                    />
                    <TextField
                        label="Password"
                        fullWidth
                        type="password"
                        size="small"
                        margin="dense"
                        required
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ borderRadius: 2, py: 1, fontWeight: 600 }}
                    >
                        Sign In (Manual)
                    </Button>
                </form>

                <Box sx={{ my: 2, display: 'flex', alignItems: 'center' }}>
                    <Divider sx={{ flexGrow: 1 }} />
                    <Typography sx={{ mx: 1, fontSize: 13 }}>or</Typography>
                    <Divider sx={{ flexGrow: 1 }} />
                </Box>

                <Button
                    variant="outlined"
                    fullWidth
                    sx={{
                        height: 40,
                        borderRadius: 2,
                        backgroundColor: 'white',
                        border: '1px solid #ccc',
                        fontWeight: 500,
                        fontSize: 14,
                        color: '#444',
                        mb: 2,
                        '&:hover': { backgroundColor: '#f5f5f5' },
                    }}
                    href="http://localhost:8080/oauth2/authorization/google"
                >
                    <img
                        src="https://developers.google.com/identity/images/g-logo.png"
                        alt="Google Login"
                        style={{ width: 20, height: 20, marginRight: 8 }}
                    />
                    Sign in with Google
                </Button>

                <Divider sx={{ mb: 2 }} />

                <Box sx={{ mt: 1.5 }}>
                    <Typography variant="body2">
                        Don’t have an account?{' '}
                        <Button variant="text" size="small" onClick={() => navigate('/register')}>
                            Create one now
                        </Button>
                    </Typography>
                </Box>

                <Typography variant="caption" sx={{ mt: 3, display: 'block', fontSize: 11 }}>
                    © 2025 BankMesh. Trusted by 1 user worldwide.
                </Typography>
            </Paper>
        </Box>
    );
}
