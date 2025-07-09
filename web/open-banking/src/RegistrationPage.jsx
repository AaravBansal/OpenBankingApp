import React, { useState, useEffect } from 'react';
import {
    Box,
    Paper,
    Typography,
    TextField,
    Button,
    MenuItem,
    Alert,
    CircularProgress,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function RegistrationPage() {
    const [form, setForm] = useState({
        firstName: '',
        lastName: '',
        preferredName: '',
        title: '',
        email: '',
        password: '',
        confirmPassword: '',
        dob: '',
    });

    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const titles = ['Mr', 'Ms', 'Mrs', 'Mx', 'Dr', 'Other'];

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const validatePassword = () =>
        form.password.length >= 8 &&
        /\d/.test(form.password) &&
        /[A-Z]/.test(form.password);

    const validateEmail = () => {
        const emailRegex = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.(com)$/;
        return emailRegex.test(form.email);
    };

    const getAge = (dob) => {
        const birth = new Date(dob);
        const now = new Date();
        let age = now.getFullYear() - birth.getFullYear();
        const m = now.getMonth() - birth.getMonth();
        if (m < 0 || (m === 0 && now.getDate() < birth.getDate())) {
            age--;
        }
        return age;
    };

    useEffect(() => {
        if (success) {
            const timer = setTimeout(() => {
                navigate('/login');
            }, 3000);
            return () => clearTimeout(timer);
        }
    }, [success, navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (form.password !== form.confirmPassword) {
            return setError('Passwords do not match.');
        }
        if (!validatePassword()) {
            return setError(
                'Password must be at least 8 characters, include a number and an uppercase letter.'
            );
        }
        if (!validateEmail()) {
            return setError('Email must be valid and end with ".com"');
        }
        const age = getAge(form.dob);
        if (age < 0 || age > 100) {
            return setError('Please enter a valid birthdate.');
        }

        const payload = { ...form, googleUser: false, registered: true };
        setLoading(true);

        try {
            const res = await fetch('http://localhost:8080/api/users/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });

            if (res.ok) {
                setSuccess(true);
            } else {
                const data = await res.json();
                if (data.message && data.message.toLowerCase().includes('email')) {
                    setError('Email is already in use.');
                } else {
                    setError(data.message || 'Registration failed');
                }
            }
        } catch (err) {
            setError('Server error. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const ageWarning =
        form.dob && new Date().getFullYear() - new Date(form.dob).getFullYear() < 18;

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
                    p: 4,
                    bgcolor: 'white',
                    textAlign: 'center',
                }}
            >
                <Box sx={{ mb: 2 }}>
                    <img
                        src="/bankmesh-logo.png"
                        alt="BankMesh Logo"
                        style={{ width: '100%', maxWidth: 260, borderRadius: 16, margin: 16 }}
                    />
                </Box>

                {success ? (
                    <>
                        <Typography variant="h4" fontWeight="700" gutterBottom>
                            You're Registered!
                        </Typography>
                        <Typography variant="body2" sx={{ mb: 3, color: 'text.secondary' }}>
                            Redirecting you to login...
                        </Typography>
                        <CircularProgress size={32} />
                    </>
                ) : (
                    <>
                        <Typography variant="h4" fontWeight="700" gutterBottom>
                            Create Your Account
                        </Typography>
                        <Typography variant="body2" sx={{ mb: 3, color: 'text.secondary' }}>
                            Start banking smarter today.
                        </Typography>

                        {error && (
                            <Alert severity="error" sx={{ mb: 2, textAlign: 'left' }}>
                                {error}
                            </Alert>
                        )}

                        <Box component="form" onSubmit={handleSubmit} noValidate>
                            <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
                                <TextField
                                    name="firstName"
                                    label="First Name"
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.firstName}
                                    onChange={handleChange}
                                />
                                <TextField
                                    name="lastName"
                                    label="Last Name"
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.lastName}
                                    onChange={handleChange}
                                />
                            </Box>

                            <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
                                <TextField
                                    name="title"
                                    label="Title"
                                    select
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.title}
                                    onChange={handleChange}
                                >
                                    {titles.map((t) => (
                                        <MenuItem key={t} value={t}>
                                            {t}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    name="dob"
                                    label="Date of Birth"
                                    type="date"
                                    size="small"
                                    required
                                    fullWidth
                                    InputLabelProps={{ shrink: true }}
                                    value={form.dob}
                                    onChange={handleChange}
                                />
                            </Box>
                            {form.dob && (
                                <Typography
                                    variant="caption"
                                    sx={{
                                        color:
                                            getAge(form.dob) < 0 || getAge(form.dob) > 100
                                                ? 'error.main'
                                                : getAge(form.dob) < 18
                                                    ? 'warning.main'
                                                    : 'success.main',
                                        textAlign: 'left',
                                        mt: 1,
                                        ml: 0.5,
                                    }}
                                >
                                    {getAge(form.dob) < 0 || getAge(form.dob) > 100
                                        ? '✗ Please enter a valid birthdate.'
                                        : getAge(form.dob) < 18
                                            ? '⚠️ You’re under 18. Parental consent may be required.'
                                            : '✓ Age verified'}
                                </Typography>
                            )}

                            <Box sx={{ mb: 2 }}>
                                <TextField
                                    name="email"
                                    label="Email Address"
                                    type="email"
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.email}
                                    onChange={handleChange}
                                />
                                {form.email && (
                                    <Typography
                                        variant="caption"
                                        sx={{
                                            color: validateEmail() ? 'success.main' : 'error.main',
                                            textAlign: 'left',
                                            mt: 0.5,
                                            ml: 0.5,
                                        }}
                                    >
                                        {validateEmail()
                                            ? '✓ Valid email'
                                            : '✗ Invalid email. Only letters and numbers allowed, must end in .com'}
                                    </Typography>
                                )}
                            </Box>

                            <Box sx={{ display: 'flex', gap: 2, mb: 1.5 }}>
                                <TextField
                                    name="password"
                                    label="Password"
                                    type="password"
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.password}
                                    onChange={handleChange}
                                />
                                <TextField
                                    name="confirmPassword"
                                    label="Confirm Password"
                                    type="password"
                                    size="small"
                                    required
                                    fullWidth
                                    value={form.confirmPassword}
                                    onChange={handleChange}
                                />
                            </Box>

                            <Typography
                                variant="caption"
                                sx={{
                                    color: 'text.secondary',
                                    textAlign: 'left',
                                    display: 'block',
                                    mb: 3,
                                }}
                            >
                                Must include: 1 uppercase, 1 number, 8+ characters
                            </Typography>

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{
                                    py: 1.5,
                                    fontWeight: 600,
                                    borderRadius: 2,
                                    background: 'linear-gradient(to right, #0a5e96, #377ba9)',
                                    '&:hover': {
                                        background: 'linear-gradient(to right, #094a72, #285a7c)',
                                    },
                                }}
                                disabled={loading}
                            >
                                {loading ? 'Registering...' : 'Sign Up'}
                            </Button>
                        </Box>

                        <Typography variant="body2" sx={{ mt: 3 }}>
                            Already have an account?{' '}
                            <Button
                                variant="text"
                                size="small"
                                onClick={() => navigate('/login')}
                                sx={{ fontSize: 14, textTransform: 'none' }}
                            >
                                Login here
                            </Button>
                        </Typography>
                    </>
                )}

                <Typography variant="caption" sx={{ mt: 3, color: 'text.secondary' }}>
                    © 2025 BankMesh. Trusted by 1 user worldwide.
                </Typography>
            </Paper>
        </Box>
    );
}
