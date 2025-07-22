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
    Modal,
    Fade,
    Backdrop,
    TextField,
    FormControl,
    InputLabel,
    Select,
    MenuItem as SelectMenuItem,
    CircularProgress,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home';

const Dashboard = () => {
    const [userFullName, setUserFullName] = useState('');
    const [anchorEl, setAnchorEl] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [modalContent, setModalContent] = useState('');
    const [userData, setUserData] = useState({
        firstName: '',
        lastName: '',
        preferredName: '',
        title: '',
        email: '',
        isGoogleUser: false,
    });

    const [preferredName, setPreferredName] = useState('');
    const [title, setTitle] = useState('');
    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');

    // Bank accounts & loading states
    const [bankAccounts, setBankAccounts] = useState([]);
    const [loadingAccounts, setLoadingAccounts] = useState(false);

    // For Add Account flow
    const [addingAccount, setAddingAccount] = useState(false);
    const [loadingNewAccounts, setLoadingNewAccounts] = useState(false);

    useEffect(() => {
        // Simulate fetching user data for dashboard
        fetch('/me')
            .then((res) => res.json())
            .then((data) => {
                if (data.firstName && data.lastName) {
                    setUserFullName(`${data.firstName} ${data.lastName}`);
                    setUserData({
                        firstName: data.firstName,
                        lastName: data.lastName,
                        preferredName: data.preferredName || '',
                        title: data.title || '',
                        email: data.email || '',
                        isGoogleUser: data.isGoogleUser || false,
                    });
                    setPreferredName(data.preferredName || '');
                    setTitle(data.title || '');
                } else {
                    setUserFullName('User Name');
                }
            })
            .catch(() => setUserFullName('User Name'));
    }, []);

    const handleMenuOpen = (event) => setAnchorEl(event.currentTarget);
    const handleMenuClose = () => setAnchorEl(null);

    const openSection = (section) => {
        setModalContent(section);
        setOpenModal(true);
        handleMenuClose();
    };

    const handleCloseModal = () => {
        setOpenModal(false);
        setPassword('');
        setPasswordError('');
    };

    const validatePassword = (pwd) => {
        if (pwd.length > 0 && pwd.length < 8) {
            setPasswordError('Password must be at least 8 characters');
            return false;
        }
        setPasswordError('');
        return true;
    };

    const handleAccountPreferencesSubmit = () => {
        if (!validatePassword(password)) return;

        const payload = {
            preferredName,
            title,
            ...(password.length > 0 ? { password } : {}),
        };

        fetch('/api/user/update', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload),
        })
            .then((res) => {
                if (res.ok) {
                    alert('Account Preferences updated successfully');
                    setUserData((prev) => ({
                        ...prev,
                        preferredName,
                        title,
                    }));
                    setUserFullName(`${userData.firstName} ${userData.lastName}`);
                    handleCloseModal();
                } else {
                    alert('Failed to update preferences');
                }
            })
            .catch(() => alert('Failed to update preferences'));
    };

    // Fetch existing accounts from backend
    const fetchAccounts = () => {
        setLoadingAccounts(true);
        fetch('/account-details')
            .then((res) => {
                if (!res.ok) throw new Error('Failed to load account details');
                return res.json();
            })
            .then((data) => {
                setBankAccounts(data || []);
            })
            .catch((err) => alert('Error loading accounts: ' + err.message))
            .finally(() => setLoadingAccounts(false));
    };

    // Called when user clicks Payments NZ button to add accounts
    const loadPaymentsNZAccounts = () => {
        setLoadingNewAccounts(true);
        setTimeout(() => {
            fetch('/account-details')
                .then((res) => {
                    if (!res.ok) throw new Error('Failed to load account details');
                    return res.json();
                })
                .then((data) => {
                    setBankAccounts((prev) => [...prev, ...(data || [])]);
                    setAddingAccount(false);
                })
                .catch((err) => alert('Error loading accounts: ' + err.message))
                .finally(() => setLoadingNewAccounts(false));
        }, 2500); // 2.5 seconds fake delay for loading spinner
    };

    // New function added exactly here:
    const loadPaymentsNZRealAccounts = () => {
        setLoadingNewAccounts(true);
        setTimeout(() => {
            fetch('http://localhost:8080/api/accounts') // <-- your real API endpoint here
                .then((res) => {
                    if (!res.ok) throw new Error('Failed to load real account details');
                    return res.json();
                })
                .then((data) => {
                    setBankAccounts((prev) => [...prev, ...(data || [])]);
                    setAddingAccount(false);
                })
                .catch((err) => alert('Error loading real accounts: ' + err.message))
                .finally(() => setLoadingNewAccounts(false));
        }, 2500);
    };

    const renderModalContent = () => {
        if (modalContent === 'Account Preferences') {
            return (
                <Box sx={{ minWidth: 360 }}>
                    <Typography variant="h5" fontWeight="bold" mb={3}>
                        Account Preferences
                    </Typography>
                    <Typography variant="body2" mb={3} color="text.secondary">
                        Manage your personal preferences below. You can update your preferred name,
                        title, and password here.
                    </Typography>
                    <TextField
                        label="Preferred Name"
                        value={preferredName}
                        fullWidth
                        onChange={(e) => setPreferredName(e.target.value)}
                        sx={{ mb: 3 }}
                    />
                    <FormControl fullWidth sx={{ mb: 3 }}>
                        <InputLabel>Title</InputLabel>
                        <Select value={title} label="Title" onChange={(e) => setTitle(e.target.value)}>
                            <SelectMenuItem value="">None</SelectMenuItem>
                            <SelectMenuItem value="Mr">Mr</SelectMenuItem>
                            <SelectMenuItem value="Mrs">Mrs</SelectMenuItem>
                            <SelectMenuItem value="Ms">Ms</SelectMenuItem>
                            <SelectMenuItem value="Dr">Dr</SelectMenuItem>
                        </Select>
                    </FormControl>
                    <TextField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value);
                            validatePassword(e.target.value);
                        }}
                        fullWidth
                        sx={{ mb: 1 }}
                        disabled={userData.isGoogleUser}
                        helperText={
                            userData.isGoogleUser
                                ? 'Password cannot be changed for Google accounts'
                                : passwordError || 'Leave blank to keep current password'
                        }
                        error={!!passwordError}
                    />
                    <Box sx={{ mt: 4, textAlign: 'right' }}>
                        <Button
                            variant="contained"
                            onClick={handleAccountPreferencesSubmit}
                            disabled={userData.isGoogleUser && password.length > 0}
                            sx={{ bgcolor: '#1e90ff', '&:hover': { bgcolor: '#0056b3' } }}
                        >
                            Save Changes
                        </Button>
                    </Box>
                </Box>
            );
        }
        if (modalContent === 'Security and Privacy') {
            return (
                <Box sx={{ maxWidth: 500 }}>
                    <Typography variant="h5" fontWeight="bold" mb={3}>
                        Security and Privacy
                    </Typography>
                    <Typography variant="body1" paragraph>
                        At Bank Mesh, your security and privacy are our top priority. We employ advanced
                        encryption protocols, multi-factor authentication, and continuous monitoring to
                        protect your personal and financial information.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        Please ensure you keep your account credentials confidential. Never share your
                        password or verification codes with anyone. Bank Mesh will never ask for your
                        password via email or phone.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        We comply fully with data protection laws and never sell your personal information
                        to third parties. You have the right to access, correct, and delete your personal
                        data at any time via your account settings.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        For additional security, we recommend enabling two-factor authentication in your
                        account preferences and regularly reviewing your login activity.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        If you have any concerns about your account security or privacy, please contact our
                        support team immediately.
                    </Typography>
                </Box>
            );
        }
        if (modalContent === 'Help and Support') {
            return (
                <Box sx={{ maxWidth: 450 }}>
                    <Typography variant="h5" fontWeight="bold" mb={3}>
                        Help and Support
                    </Typography>
                    <Typography variant="body1" paragraph>
                        Need assistance? Our support team is here to help you 24/7. Whether you have questions
                        about your account, transactions, or technical issues, we’re ready to assist.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        Visit our{' '}
                        <a href="https://www.bankmesh.co.nz/support" target="_blank" rel="noreferrer">
                            Support Center
                        </a>{' '}
                        for FAQs, guides, and troubleshooting tips.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        You can also contact us directly via live chat or email support@bankmesh.co.nz.
                    </Typography>
                    <Typography variant="body1" paragraph>
                        For urgent issues, please call our hotline: 0800 BANK MESH (0800 2265 6374).
                    </Typography>
                </Box>
            );
        }
        if (modalContent === 'Sign Out') {
            return (
                <Box sx={{ maxWidth: 360, textAlign: 'center' }}>
                    <Typography variant="h5" fontWeight="bold" mb={3}>
                        Confirm Sign Out
                    </Typography>
                    <Typography variant="body1" mb={4}>
                        Are you sure you want to sign out of your Bank Mesh account?
                    </Typography>
                    <Box sx={{ display: 'flex', justifyContent: 'center', gap: 2 }}>
                        <Button
                            variant="contained"
                            color="error"
                            onClick={() => {
                                alert('Signed out');
                                handleCloseModal();
                            }}
                        >
                            Sign Out
                        </Button>
                        <Button variant="outlined" onClick={handleCloseModal}>
                            Cancel
                        </Button>
                    </Box>
                </Box>
            );
        }
        return (
            <Box sx={{ minWidth: 400 }}>
                <Typography variant="h5" fontWeight="bold" mb={2}>
                    {modalContent}
                </Typography>
                <Typography variant="body1">
                    This is a placeholder for <strong>{modalContent}</strong> settings.
                </Typography>
            </Box>
        );
    };

    return (
        <Box
            sx={{
                minHeight: '100vh',
                background: 'linear-gradient(135deg, #1E3C72, #2A5298)',
                color: '#fff',
                pb: 4,
            }}
        >
            <AppBar
                position="static"
                color="transparent"
                elevation={0}
                sx={{ borderBottom: '1px solid #ddd', bgcolor: '#fff' }}
            >
                <Toolbar sx={{ justifyContent: 'space-between' }}>
                    <Stack direction="row" alignItems="center" spacing={4}>
                        <Box
                            component="img"
                            src="/bankmesh-logo.png"
                            alt="Bank Mesh Logo"
                            sx={{ height: 48, width: 'auto', cursor: 'pointer' }}
                            onClick={() => (window.location.href = '/dashboard')}
                        />
                        <Typography variant="h6" fontWeight="bold" sx={{ cursor: 'pointer', color: '#1e90ff' }}>
                            Dashboard
                        </Typography>
                        <Button color="primary" sx={{ textTransform: 'none', color: '#1e90ff' }}>
                            Accounts
                        </Button>
                        <Button color="primary" sx={{ textTransform: 'none', color: '#1e90ff' }}>
                            Transactions
                        </Button>
                    </Stack>

                    <Stack direction="row" alignItems="center" spacing={1}>
                        <Typography
                            variant="body1"
                            sx={{ color: '#333', fontWeight: 600, whiteSpace: 'nowrap' }}
                        >
                            {userFullName}
                        </Typography>

                        <IconButton
                            edge="end"
                            color="inherit"
                            aria-label="menu"
                            onClick={handleMenuOpen}
                            size="large"
                            sx={{ ml: 1, color: '#1e90ff' }}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                            PaperProps={{ sx: { mt: 1.5, minWidth: 200 } }}
                        >
                            <MenuItem onClick={() => openSection('Account Preferences')}>
                                Account Preferences
                            </MenuItem>
                            <MenuItem onClick={() => openSection('Security and Privacy')}>
                                Security and Privacy
                            </MenuItem>
                            <MenuItem onClick={() => openSection('Help and Support')}>Help and Support</MenuItem>
                            <MenuItem onClick={() => openSection('Sign Out')}>Sign Out</MenuItem>
                        </Menu>
                    </Stack>
                </Toolbar>
            </AppBar>

            <Box sx={{ maxWidth: 900, mx: 'auto', mt: 5, px: 2 }}>
                {bankAccounts.map((account) => (
                    <Paper
                        key={account.accountId}
                        elevation={3}
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            bgcolor: '#fff',
                            borderRadius: 3,
                            mb: 3,
                            p: 3,
                            color: '#000',
                        }}
                    >
                        <Box sx={{ mr: 3 }}>
                            <HomeIcon sx={{ fontSize: 56, color: '#1e90ff' }} />
                        </Box>
                        <Box sx={{ flexGrow: 1 }}>
                            <Typography variant="h6" fontWeight="bold">
                                {account.title}
                            </Typography>
                            <Typography>Nickname: {account.nickname}</Typography>
                            <Typography>
                                Type: {account.accountType} {account.accountSubType}
                            </Typography>
                            <Typography>
                                Balance: {account.balance} {account.currency}
                            </Typography>
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
                                onClick={() => {
                                    // Map bankId to URLs as you like
                                    const bankUrlMap = {
                                        BNZNZ22: 'https://www.bnz.co.nz',
                                        ASBNZ22: 'https://www.asb.co.nz',
                                    };
                                    const url = bankUrlMap[account.bankId] || 'https://www.bankmesh.co.nz';
                                    window.open(url, '_blank');
                                }}
                            >
                                Bank Mesh
                            </Button>
                            <Typography sx={{ fontSize: 12, opacity: 0.7 }}>Open</Typography>
                        </Box>
                    </Paper>
                ))}

                {!addingAccount && (
                    <Paper
                        elevation={3}
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            bgcolor: 'rgba(255,255,255,0.40)',
                            borderRadius: 3,
                            p: 3,
                            justifyContent: 'center',
                            color: '#fff',
                            cursor: 'pointer',
                            fontStyle: 'italic',
                            fontWeight: 600,
                        }}
                        onClick={() => setAddingAccount(true)}
                    >
                        Add another account
                    </Paper>
                )}

                {addingAccount && (
                    <Paper
                        elevation={3}
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            bgcolor: 'rgba(255,255,255,0.40)',
                            borderRadius: 3,
                            p: 3,
                            justifyContent: 'center',
                            color: '#fff',
                            fontWeight: 600,
                        }}
                    >
                        {!loadingNewAccounts && (
                            <>
                                <Typography sx={{ mb: 2 }}>Select Account Provider:</Typography>
                                <Button
                                    variant="contained"
                                    sx={{ mb: 1, bgcolor: '#1e90ff', '&:hover': { bgcolor: '#0056b3' } }}
                                    onClick={loadPaymentsNZAccounts}
                                >
                                    Payments NZ
                                </Button>

                                {/* NEW PNZ (Real) button added below */}
                                <Button
                                    variant="contained"
                                    sx={{ mb: 1, bgcolor: '#1e90ff', '&:hover': { bgcolor: '#0056b3' } }}
                                    onClick={loadPaymentsNZRealAccounts}
                                >
                                    PNZ (Real)
                                </Button>

                                <Button
                                    variant="outlined"
                                    sx={{ color: '#fff' }}
                                    onClick={() => setAddingAccount(false)}
                                >
                                    Cancel
                                </Button>
                            </>
                        )}

                        {loadingNewAccounts && (
                            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                                <CircularProgress sx={{ color: '#1e90ff', mb: 2.5 }} />
                                <Typography>Loading accounts from Payments NZ...</Typography>
                            </Box>
                        )}
                    </Paper>
                )}
            </Box>

            {/* Modal for hamburger sections */}
            <Modal
                open={openModal}
                onClose={handleCloseModal}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
                slotProps={{ backdrop: { timeout: 300 } }}
            >
                <Fade in={openModal}>
                    <Box
                        sx={{
                            position: 'absolute',
                            top: '50%',
                            left: '50%',
                            transform: 'translate(-50%, -50%)',
                            bgcolor: 'background.paper',
                            borderRadius: 3,
                            boxShadow: 24,
                            p: 4,
                            minWidth: 320,
                            maxWidth: 600,
                            maxHeight: '80vh',
                            overflowY: 'auto',
                            outline: 'none',
                        }}
                    >
                        {renderModalContent()}
                    </Box>
                </Fade>
            </Modal>
        </Box>
    );
};

export default Dashboard;
