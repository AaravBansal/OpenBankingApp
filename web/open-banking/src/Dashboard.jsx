import React, { useEffect, useState } from 'react';
import {
    Container,
    Typography,
    Paper,
    CircularProgress,
    Alert,
} from '@mui/material';

export default function Dashboard() {
    const [data, setData] = useState(null);
    const [error, setError] = useState('');

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

    return (
        <Container maxWidth="sm">
            <Paper sx={{ mt: 8, p: 4 }}>
                <Typography variant="h5" gutterBottom>
                    Dashboard - Planet Data
                </Typography>
                {error && <Alert severity="error">{error}</Alert>}
                {!data && !error && <CircularProgress />}
                {data && (
                    <pre style={{ whiteSpace: 'pre-wrap', wordWrap: 'break-word' }}>
            {data}
          </pre>
                )}
            </Paper>
        </Container>
    );
}
