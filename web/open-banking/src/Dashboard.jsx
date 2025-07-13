import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    IconButton,
    Menu,
    MenuItem,
    Stack,
    Paper,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home';

const Dashboard = () => {
    const [userFullName, setUserFullName] = useState('');
    const [anchorEl, setAnchorEl] = useState(null);

    useEffect(() => {
        fetch('/me')
            .then((res) => res.json())
            .then((data) => {
                if (data.firstName && data.lastName) {
                    setUserFullName(`${data.firstName} ${data.lastName}`);
                } else {
                    setUserFullName('User Name');
                }
            })
            .catch(() => setUserFullName('User Name'));
    }, []);

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const bankAccounts = [
        {
            id: 1,
            title: 'Primary House Loan - BNZ',
            balance: '$430,000',
            interestRate: '5.6%',
            monthlyPayment: '$2,150',
            nextPaymentDue: '15 August 2025',
            bankUrl: 'https://secure.bnz.co.nz/auth/personal-login',
        },
        {
            id: 2,
            title: 'Car Loan - ASB',
            balance: '$12,300',
            interestRate: '6.8%',
            monthlyPayment: '$350',
            nextPaymentDue: '1 August 2025',
            bankUrl: 'https://online.asb.co.nz/auth/?fm=header:login',
        },
    ];

    return (
        <Box
            sx={{
                minHeight: '100vh',
                background: 'linear-gradient(135deg, #1E3C72, #2A5298)',
                color: '#fff',
                pb: 4,
            }}
        >
            <AppBar position="static" color="transparent" elevation={0} sx={{ borderBottom: '1px solid #ddd', bgcolor: '#fff' }}>
                <Toolbar sx={{ justifyContent: 'space-between' }}>
                    <Stack direction="row" alignItems="center" spacing={4}>
                        <Box
                            component="img"
                            src="/bankmesh-logo.png"
                            alt="Bank Mesh Logo"
                            sx={{
                                height: 48,
                                width: 'auto',
                                cursor: 'pointer',
                            }}
                            onClick={() => window.location.href = '/dashboard'}
                        />
                        <Typography variant="h6" color="primary" fontWeight="bold" sx={{ cursor: 'pointer' }}>
                            Dashboard
                        </Typography>
                        <Button color="primary" sx={{ textTransform: 'none' }}>
                            Accounts
                        </Button>
                        <Button color="primary" sx={{ textTransform: 'none' }}>
                            Transactions
                        </Button>
                    </Stack>

                    <Stack direction="row" alignItems="center" spacing={1}>
                        <Typography variant="body1" sx={{ color: '#333', fontWeight: 600, whiteSpace: 'nowrap' }}>
                            {userFullName}
                        </Typography>

                        <IconButton
                            edge="end"
                            color="inherit"
                            aria-label="menu"
                            onClick={handleMenuOpen}
                            size="large"
                            sx={{ ml: 1, color: '#333' }}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                            PaperProps={{
                                sx: {
                                    mt: 1.5,
                                    minWidth: 180,
                                },
                            }}
                        >
                            <MenuItem onClick={() => { handleMenuClose(); alert('Redirect to Account Preferences') }}>
                                Account Preferences
                            </MenuItem>
                            <MenuItem onClick={() => { handleMenuClose(); alert('Redirect to Security and Privacy') }}>
                                Security and Privacy
                            </MenuItem>
                            <MenuItem onClick={() => { handleMenuClose(); alert('Redirect to Help and Support') }}>
                                Help and Support
                            </MenuItem>
                            <MenuItem onClick={() => { handleMenuClose(); alert('Signing Out...') }}>
                                Sign Out
                            </MenuItem>
                        </Menu>
                    </Stack>
                </Toolbar>
            </AppBar>

            <Box sx={{ maxWidth: 900, mx: 'auto', mt: 5, px: 2 }}>
                {bankAccounts.map((account) => (
                    <Paper
                        key={account.id}
                        elevation={3}
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            bgcolor: '#fff',
                            borderRadius: 3,
                            mb: 3,
                            p: 3,
                            color: '#333',
                        }}
                    >
                        <Box sx={{ mr: 3 }}>
                            <HomeIcon sx={{ fontSize: 56, color: '#6cb2eb' }} />
                        </Box>
                        <Box sx={{ flexGrow: 1 }}>
                            <Typography variant="h6" fontWeight="bold">
                                {account.title}
                            </Typography>
                            <Typography>Outstanding Balance: {account.balance}</Typography>
                            <Typography>
                                Interest Rate: {account.interestRate} | Monthly Payment: {account.monthlyPayment}
                            </Typography>
                            <Typography>Next Payment Due: {account.nextPaymentDue}</Typography>
                        </Box>
                        <Box sx={{ textAlign: 'right' }}>
                            <Button
                                variant="contained"
                                sx={{
                                    mb: 1,
                                    bgcolor: '#1e90ff',
                                    '&:hover': { bgcolor: '#0056b3' },
                                    textTransform: 'none',
                                }}
                                onClick={() => window.open(account.bankUrl, '_blank')}
                            >
                                Bank Mesh
                            </Button>
                            <Typography sx={{ fontSize: 12, opacity: 0.7 }}>Open</Typography>
                        </Box>
                    </Paper>
                ))}

                <Paper
                    elevation={3}
                    sx={{
                        display: 'flex',
                        alignItems: 'center',
                        bgcolor: 'rgba(255,255,255,0.3)',
                        borderRadius: 3,
                        p: 3,
                        justifyContent: 'center',
                        color: '#fff',
                        cursor: 'pointer',
                        fontStyle: 'italic',
                        fontWeight: 600,
                    }}
                    onClick={() => alert('Add another account clicked')}
                >
                    Add another account
                </Paper>
            </Box>
        </Box>
    );
};

export default Dashboard;
