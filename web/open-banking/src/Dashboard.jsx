import React, { useEffect, useState } from 'react';
import {
    Container,
    Typography,
    Box,
    Paper,
    CircularProgress,
    Button,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
    const [user, setUser] = useState(null);
    const [planets, setPlanets] = useState({});
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch logged-in user info
        fetch('http://localhost:8080/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Unauthorized');
                return res.json();
            })
            .then(data => setUser(data))
            .catch(err => {
                console.error(err);
                navigate('/login');
            });

        // Fetch deck shuffle info from /planets
        fetch('http://localhost:8080/planets', {
            method: 'GET',
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                setPlanets(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, [navigate]);

    const handleLogout = () => {
        fetch('http://localhost:8080/logout', {
            method: 'POST',
            credentials: 'include',
        })
            .then(() => navigate('/login'))
            .catch(err => console.error('Logout failed', err));
    };

    if (loading) {
        return (
            <Box
                sx={{
                    height: '100vh',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <CircularProgress />
            </Box>
        );
    }

    return (
        <Container maxWidth="md" sx={{ mt: 5 }}>
            <Paper sx={{ p: 4, borderRadius: 4 }}>
                <Typography variant="h4" gutterBottom>
                    Welcome to your Dashboard!
                </Typography>
                {user && (
                    <Typography variant="body1" sx={{ mb: 2 }}>
                        Logged in as: <strong>{user.firstName}</strong>
                    </Typography>
                )}

                <Typography variant="h6" sx={{ mt: 2 }}>
                    Deck Shuffle Info:
                </Typography>
                <ul>
                    <li>Deck ID: {planets.deck_id}</li>
                    <li>Shuffled: {planets.shuffled ? 'Yes' : 'No'}</li>
                    <li>Remaining Cards: {planets.remaining}</li>
                </ul>

                <Box sx={{ mt: 4 }}>
                    <Button variant="outlined" color="secondary" onClick={handleLogout}>
                        Logout
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
}
