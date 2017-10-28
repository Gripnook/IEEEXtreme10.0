#include <iostream>
#include <vector>

using namespace std;

using state = int;
using Set = vector<bool>;
using Queue = vector<state>;

bool get(const state &board, int x, int y)
{
    return (board & (1 << ((5 * x) + y))) > 0;
}

void setbit(state &board, int x, int y, bool value)
{
    if (value)
    {
        board |= (1 << ((5 * x) + y));
    }
    else
    {
        board &= ~(1 << ((5 * x) + y));
    }
}

void flip(state &board, int x, int y)
{
    board ^= (1 << ((5 * x) + y));
}

void swap(state &board, int x1, int y1, int x2, int y2)
{
    state idx1 = (1 << ((5 * x1) + y1));
    state idx2 = (1 << ((5 * x2) + y2));
    bool v1 = board & idx1;
    bool v2 = board & idx2;
    if (v1)
    {
        board |= idx2;
    }
    else
    {
        board &= ~idx2;
    }
    if (v2)
    {
        board |= idx1;
    }
    else
    {
        board &= ~idx1;
    }
}

constexpr int masksH[]{0x01, 0x03, 0x07, 0x0F, 0x1F};

void swapH(state &board, int x1, int x2, int y, int n)
{
    int xx1 = (x1 << 2) + x1;
    int xx2 = (x2 << 2) + x2;
    int delta = xx2 - xx1;
    state idx1 = masksH[n - 1] << (xx1 + y);
    state idx2 = masksH[n - 1] << (xx2 + y);
    state v1 = (board & idx1) << (delta);
    state v2 = (board & idx2) >> (delta);
    board &= ~(idx1 | idx2);
    board |= v1 | v2;
}

constexpr int masksV[]{0x00000001, 0x00000021, 0x00000421, 0x00008421, 0x00108421};

void swapV(state &board, int x, int y1, int y2, int n)
{
    int xx = (x << 2) + x;
    int delta = (y2 - y1);
    state idx1 = masksV[n - 1] << (xx + y1);
    state idx2 = masksV[n - 1] << (xx + y2);
    state v1 = (board & idx1) << (delta);
    state v2 = (board & idx2) >> (delta);
    board &= ~(idx1 | idx2);
    board |= v1 | v2;
}

state flipH(state board, int x0, int y0, int n)
{
    int cl = (x0 << 1) + n - 1;
    for (int i = x0; i < x0 + n / 2; ++i)
    {
        swapH(board, i, cl - i, y0, n);
    }
    return board;
}

state flipV(state board, int x0, int y0, int n)
{
    int cl = (y0 << 1) + n - 1;
    for (int j = y0; j < y0 + n / 2; ++j)
    {
        swapV(board, x0, j, cl - j, n);
    }
    return board;
}

constexpr int masksInv[]{0x00000001, 0x00000063, 0x00001CE7, 0x0007BDEF, 0x01FFFFFF};

state invert(state board, int x0, int y0, int n)
{
    state idx = masksInv[n - 1] << ((5 * x0) + y0);
    return board ^ idx;
}

bool add(Queue &q, const state &board, Set &dp, Set &otherdp)
{
    for (int n = 1; n <= 5; ++n)
    {
        for (int i = 0; i <= 5 - n; ++i)
        {
            for (int j = 0; j <= 5 - n; ++j)
            {
                auto next = invert(board, i, j, n);
                if (!dp[next])
                {
                    if (otherdp[next])
                    {
                        return true;
                    }
                    dp[next] = true;
                    q.push_back(next);
                }
                next = flipH(board, i, j, n);
                if (!dp[next])
                {
                    if (otherdp[next])
                    {
                        return true;
                    }
                    dp[next] = true;
                    q.push_back(next);
                }
                next = flipV(board, i, j, n);
                if (!dp[next])
                {
                    if (otherdp[next])
                    {
                        return true;
                    }
                    dp[next] = true;
                    q.push_back(next);
                }
            }
        }
    }
    return false;
}

Queue front;
Queue back;
vector<bool> dpfront(1 << 25);
vector<bool> dpback(1 << 25);

int search(const state &initial, const state &goal)
{
    if (initial == goal)
    {
        return 0;
    }
    front.clear();
    int frontIdx = 0;
    back.clear();
    int backIdx = 0;
    for (int i = 0; i < (1 << 25); ++i)
    {
        dpfront[i] = false;
        dpback[i] = false;
    }
    int frontDepth = 0;
    int backDepth = 0;
    front.push_back(initial);
    dpfront[initial] = true;
    back.push_back(goal);
    dpback[goal] = true;
    while (true)
    {
        int frontSize = front.size();
        while (frontIdx < frontSize)
        {
            auto current = front[frontIdx++];
            if (add(front, current, dpfront, dpback))
            {
                return frontDepth + backDepth + 1;
            }
        }
        ++frontDepth;
        int backSize = back.size();
        while (backIdx < backSize)
        {
            auto current = back[backIdx++];
            if (add(back, current, dpback, dpfront))
            {
                return frontDepth + backDepth + 1;
            }
        }
        ++backDepth;
    }
}

int main()
{
    int t;
    cin >> t;
    front.reserve(1 << 25);
    back.reserve(1 << 25);
    while (t--)
    {
        state initial{};
        state goal{};
        char ch;
        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                cin >> ch;
                if (ch == 'X')
                {
                    setbit(initial, i, j, true);
                }
            }
            for (int j = 0; j < 5; ++j)
            {
                cin >> ch;
                if (ch == 'X')
                {
                    setbit(goal, i, j, true);
                }
            }
        }
        cout << search(initial, goal) << endl;
    }
    return 0;
}
