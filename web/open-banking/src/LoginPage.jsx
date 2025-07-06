import React, { useState } from 'react';
import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Box,
    Divider,
} from '@mui/material';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleHardcodedLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ username, password }),
                credentials: 'include',
                redirect: 'manual',
            });

            if (res.status === 302 || res.status === 301) {
                const location = res.headers.get('Location');
                if (location) {
                    window.location.href = location;
                    return;
                }
            }

            if (!res.ok) throw new Error('Login failed');

            alert('Login failed.');
        } catch (err) {
            alert('Login failed.');
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
                fontFamily: `'Segoe UI', sans-serif`,
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
                {/* Logo */}
                <Box sx={{ mb: 2 }}>
                    <img
                        src="/bankmesh-logo.png"
                        alt="BankMesh Logo"
                        style={{
                            width: '100%',
                            maxWidth: 260,
                            borderRadius: '16px',
                        }}
                    />
                </Box>

                {/* Heading */}
                <Typography
                    variant="h4"
                    fontWeight="700"
                    gutterBottom
                    color="text.primary"
                    sx={{ fontSize: '2.2rem' }}
                >
                    Welcome
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                    Sign in to your account
                </Typography>

                {/* Login Form */}
                <Box component="form" onSubmit={handleHardcodedLogin} sx={{ width: '100%' }}>
                    <TextField
                        label="Email Address"
                        fullWidth
                        size="small"
                        margin="dense"
                        required
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        InputLabelProps={{ shrink: true }}
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
                        InputLabelProps={{ shrink: true }}
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
                </Box>

                {/* Divider with OR */}
                <Box sx={{ my: 2, display: 'flex', alignItems: 'center' }}>
                    <Divider sx={{ flexGrow: 1 }} />
                    <Typography sx={{ mx: 1, color: 'text.secondary', fontSize: 13 }}>
                        or
                    </Typography>
                    <Divider sx={{ flexGrow: 1 }} />
                </Box>

                {/* Google Login Button */}
                <Button
                    variant="outlined"
                    fullWidth
                    sx={{
                        height: 40,
                        borderRadius: 2,
                        backgroundColor: 'white',
                        border: '1px solid #ccc',
                        textTransform: 'none',
                        fontWeight: 500,
                        fontSize: 14,
                        color: '#444',
                        mb: 2,
                        '&:hover': {
                            backgroundColor: '#f5f5f5',
                        },
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

                {/* Divider UNDER Google login */}
                <Divider sx={{ mb: 2 }} />

                {/* Inline Register Prompt */}
                <Box sx={{ mt: 1.5, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Typography variant="body2" color="text.secondary" sx={{ mr: 1 }}>
                        Don’t have an account?
                    </Typography>
                    <Button
                        variant="text"
                        size="small"
                        sx={{
                            textTransform: 'none',
                            fontSize: 13,
                            fontWeight: 500,
                            color: '#0a5e96',
                            minWidth: 0,
                            padding: 0,
                        }}
                        onClick={() => (window.location.href = '/register')}
                    >
                        Create one now
                    </Button>
                </Box>

                {/* Footer */}
                <Typography
                    variant="caption"
                    color="text.secondary"
                    sx={{ mt: 3, display: 'block', fontSize: 11 }}
                >
                    © 2025 BankMesh. Trusted by 1 user worldwide.
                </Typography>
            </Paper>
        </Box>
    );
}
