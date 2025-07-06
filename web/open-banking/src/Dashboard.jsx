import React, { useEffect, useState } from 'react';
import {
    Container,
    Typography,
    Paper,
    CircularProgress,
    Alert,
    Box,
} from '@mui/material';

export default function Dashboard() {
    const [data, setData] = useState(null);
    const [error, setError] = useState('');
    const [user, setUser] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/planets', {
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Unauthorized or failed to fetch');
                return res.text();
            })
            .then(setData)
            .catch(err => setError(err.message));
    }, []);

    useEffect(() => {
        fetch('http://localhost:8080/me', {
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to fetch user info');
                return res.json();
            })
            .then(setUser)
            .catch(() => setUser({ username: 'Not logged in' }));
    }, []);

    return (
        <Box
            sx={{
                minHeight: '100vh',
                background: 'linear-gradient(to right, #0a5e96, #377ba9)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                padding: 2,
            }}
        >
            <Container maxWidth="sm">
                <Paper
                    elevation={6}
                    sx={{
                        p: 4,
                        borderRadius: 3,
                        backgroundColor: 'white',
                    }}
                >
                    <Box sx={{ mb: 2 }}>
                        <Typography variant="h4" sx={{ color: '#0a5e96' }}>
                            Dashboard
                        </Typography>
                        {user && (
                            <Typography variant="subtitle1" sx={{ color: '#555' }}>
                                Logged in as: {user.username}
                            </Typography>
                        )}
                    </Box>

                    <Typography variant="h6" sx={{ color: '#377ba9', mb: 2 }}>
                        Planet Data
                    </Typography>

                    {error && <Alert severity="error">{error}</Alert>}
                    {!data && !error && <CircularProgress />}
                    {data && (
                        <pre style={{ whiteSpace: 'pre-wrap', wordWrap: 'break-word', color: '#333' }}>
                            {data}
                        </pre>
                    )}
                </Paper>
            </Container>
        </Box>
    );
}
