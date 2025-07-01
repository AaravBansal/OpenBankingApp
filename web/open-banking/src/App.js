import React from 'react';
import {
    Container,
    Paper,
    Typography,
    Box,
    TextField,
    Button,
} from '@mui/material';

export default function LoginPage() {
    return (
        <Container component="main" maxWidth="xs">
            <Paper
                elevation={6}
                sx={{
                    mt: 8,
                    p: 4,
                    borderRadius: 3,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography variant="h5" gutterBottom>
                    Welcome to Open Banking
                </Typography>
                <Box component="form" noValidate sx={{ mt: 2, width: '100%' }}>
                    <TextField
                        fullWidth
                        label="Username"
                        margin="normal"
                        required
                    />
                    <TextField
                        fullWidth
                        label="Password"
                        margin="normal"
                        type="password"
                        required
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3 }}
                    >
                        Sign In
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
}
