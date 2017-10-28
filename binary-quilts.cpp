#include <iostream>
#include <vector>

using namespace std;

using State = int;
using Set = vector<bool>;
using Queue = vector<State>;

bool get(const State &board, int x, int y)
{
    return (board & (1 << ((5 * x) + y))) > 0;
}

void setbit(State &board, int x, int y, bool value)
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

void flip(State &board, int x, int y)
{
    board ^= (1 << ((5 * x) + y));
}

void swap(State &board, int x1, int y1, int x2, int y2)
{
    State idx1 = (1 << ((5 * x1) + y1));
    State idx2 = (1 << ((5 * x2) + y2));
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

void swapH(State &board, int x1, int x2, int y, int n)
{
    int xx1 = (x1 << 2) + x1;
    int xx2 = (x2 << 2) + x2;
    int delta = xx2 - xx1;
    State idx1 = masksH[n - 1] << (xx1 + y);
    State idx2 = masksH[n - 1] << (xx2 + y);
    State v1 = (board & idx1) << (delta);
    State v2 = (board & idx2) >> (delta);
    board &= ~(idx1 | idx2);
    board |= v1 | v2;
}

constexpr int masksV[]{0x00000001, 0x00000021, 0x00000421, 0x00008421, 0x00108421};

void swapV(State &board, int x, int y1, int y2, int n)
{
    int xx = (x << 2) + x;
    int delta = (y2 - y1);
    State idx1 = masksV[n - 1] << (xx + y1);
    State idx2 = masksV[n - 1] << (xx + y2);
    State v1 = (board & idx1) << (delta);
    State v2 = (board & idx2) >> (delta);
    board &= ~(idx1 | idx2);
    board |= v1 | v2;
}

State flipH(State board, int x0, int y0, int n)
{
    int cl = (x0 << 1) + n - 1;
    for (int i = x0; i < x0 + n / 2; ++i)
    {
        swapH(board, i, cl - i, y0, n);
    }
    return board;
}

State flipV(State board, int x0, int y0, int n)
{
    int cl = (y0 << 1) + n - 1;
    for (int j = y0; j < y0 + n / 2; ++j)
    {
        swapV(board, x0, j, cl - j, n);
    }
    return board;
}

constexpr int masksInv[]{0x00000001, 0x00000063, 0x00001CE7, 0x0007BDEF, 0x01FFFFFF};

State invert(State board, int x0, int y0, int n)
{
    State idx = masksInv[n - 1] << ((5 * x0) + y0);
    return board ^ idx;
}

bool add(Queue &q, const State &board, Set &dp, Set &otherdp)
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

int search(const State &initial, const State &goal)
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
        State initial{};
        State goal{};
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
