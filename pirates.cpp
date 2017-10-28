#include <iostream>
#include <vector>
#include <queue>
#include <utility>

using namespace std;

struct Node
{
    int label{-1};
    int depth{0};
    Node *parent{nullptr};
    vector<Node *> children;
};

int N, M, Q;
int labelCount = 0;
vector<vector<char>> map;
vector<vector<int>> labels;
vector<vector<int>> connections;
vector<Node> nodes;

void expand(int i, int j, int label, char type);
void buildMap(int i, int j);
void buildTree();
int lca(int label1, int label2);

int main()
{
    cin >> N >> M >> Q;
    map.resize(N);
    labels.resize(N);
    for (int i = 0; i < N; ++i)
    {
        map[i].resize(M);
        labels[i].resize(M, -1);
        for (int j = 0; j < M; ++j)
        {
            cin >> map[i][j];
        }
    }

    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            if (labels[i][j] == -1)
            {
                expand(i, j, labelCount++, map[i][j]);
            }
        }
    }

    connections.resize(labelCount);
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            buildMap(i, j);
        }
    }

    buildTree();
    for (int i = 0; i < Q; ++i)
    {
        int x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        int label1 = labels[x1 - 1][y1 - 1];
        int label2 = labels[x2 - 1][y2 - 1];
        cout << lca(label1, label2) / 2 << endl;
    }

    return 0;
}

void expand(int i, int j, int label, char type)
{
    if (i < 0 || i >= N || j < 0 || j >= M)
        return;
    if (map[i][j] == type && labels[i][j] == -1)
    {
        labels[i][j] = label;
        expand(i - 1, j - 1, label, type);
        expand(i - 1, j, label, type);
        expand(i - 1, j + 1, label, type);
        expand(i, j - 1, label, type);
        expand(i, j + 1, label, type);
        expand(i + 1, j - 1, label, type);
        expand(i + 1, j, label, type);
        expand(i + 1, j + 1, label, type);
    }
}

void addLabel(int i, int j, int label)
{
    if (i < 0 || i >= N || j < 0 || j >= M)
        return;
    int nextLabel = labels[i][j];
    if (label != nextLabel)
    {
        connections[label].push_back(nextLabel);
        connections[nextLabel].push_back(label);
    }
}

void buildMap(int i, int j)
{
    int label = labels[i][j];
    addLabel(i - 1, j + 1, label);
    addLabel(i, j + 1, label);
    addLabel(i + 1, j + 1, label);
    addLabel(i + 1, j, label);
}

void buildTree(Node *node)
{
    for (int next : connections[node->label])
    {
        if (nodes[next].label == -1)
        {
            node->children.push_back(&nodes[next]);
            nodes[next].label = next;
            nodes[next].depth = node->depth + 1;
            nodes[next].parent = node;
            buildTree(&nodes[next]);
        }
    }
}

void buildTree()
{
    nodes.resize(labelCount);
    int root = labels[N / 2][M / 2];
    nodes[root].label = root;
    buildTree(&nodes[root]);
}

int lca(int label1, int label2)
{
    Node *node1 = &nodes[label1];
    Node *node2 = &nodes[label2];
    int result = 0;
    while (node1->depth > node2->depth)
    {
        ++result;
        node1 = node1->parent;
    }
    while (node2->depth > node1->depth)
    {
        ++result;
        node2 = node2->parent;
    }
    while (node1 != node2)
    {
        result += 2;
        node1 = node1->parent;
        node2 = node2->parent;
    }
    return result;
}
