import React from 'react';
import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Box,
} from '@mui/material';

export default function LoginPage() {
    return (
        <Box
            sx={{
                minHeight: '100vh',
                background: 'linear-gradient(to right, #0a5e96, #377ba9)',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                p: 2,
            }}
        >
            <Paper
                elevation={6}
                sx={{
                    p: 4,
                    borderRadius: 3,
                    width: '100%',
                    maxWidth: 400,
                }}
            >
                <Typography variant="h4" align="center" gutterBottom>
                    Welcome to BankMesh
                </Typography>

                <Box
                    component="form"
                    method="post"
                    action="http://localhost:8080/login"
                    sx={{ mt: 2 }}
                >
                    <TextField
                        name="username"
                        label="Username"
                        fullWidth
                        required
                        margin="normal"
                        sx={{
                            backgroundColor: 'white',
                            borderRadius: 1,
                            input: { color: 'black' },
                        }}
                    />
                    <TextField
                        name="password"
                        label="Password"
                        type="password"
                        fullWidth
                        required
                        margin="normal"
                        sx={{
                            backgroundColor: 'white',
                            borderRadius: 1,
                            input: { color: 'black' },
                        }}
                    />

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{
                            mt: 3,
                            backgroundColor: '#0a5e96',
                            '&:hover': { backgroundColor: '#084b70' },
                        }}
                    >
                        Sign In (Manual)
                    </Button>
                </Box>

                <Typography sx={{ mt: 2, textAlign: 'center' }}>or</Typography>

                <Button
                    fullWidth
                    variant="contained"
                    color="secondary"
                    href="http://localhost:8080/oauth2/authorization/google"
                    sx={{ mt: 2 }}
                >
                    Sign in with Google
                </Button>
            </Paper>
        </Box>
    );
}
